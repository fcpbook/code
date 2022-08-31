package chap20

import chap20.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.test.assertions.assertInOrder
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.*
import tinyscalautils.threads.Executors.global
import tinyscalautils.util.FastRandom

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class QueueTests extends AnyFunSuite:
   private val n = availableProcessors
   private val m = 1000

   private def testPutAll[Q : IntQueue](queue: Q) =
      syncForkJoin(1 until n, queue.putAll(1 to 100*))(_ => m times queue.put(0))
      val all = queue.drain()
      assert(all.containsSlice(1 to 100))

   test("putAll public") {
      testPutAll(singlepublic.ConcurrentQueue())
   }

   test("putAll private") {
      testPutAll(singleprivate.ConcurrentQueue())
   }

   test("putAll split") {
      testPutAll(split.ConcurrentQueue())
   }

   private def testDrain[Q : IntQueue](queue: Q) =
      for i <- 1 to m do queue.put(i)
      val start = CountDownLatch(3)
      withThreadPoolAndWait(global) {
         val f1 = Future {
            start.countDownAndWait()
            Iterator.fill(m)(queue.take()).count(_.nonEmpty)
         }
         val f2 = Future {
            start.countDownAndWait()
            for i <- m + 1 to 2 * m do queue.put(i)
         }
         val f3 = Future {
            start.countDownAndWait()
            queue.drain()
         }
         for count <- f1; _ <- f2; list <- f3 yield
            val list2 = queue.drain()
            assert(count + list.length + list2.length == 2 * m)
            assertInOrder(list)
            assertInOrder(list2)
      }

   test("drain public") {
      testDrain(singlepublic.ConcurrentQueue())
   }

   test("drain public alt") {
      testDrain(singlepublic.ConcurrentQueue())(using PubIntQueue(nonLockDrain = true))
   }

   test("drain private") {
      testDrain(singleprivate.ConcurrentQueue())
   }

   test("drain split") {
      testDrain(split.ConcurrentQueue())
   }

   private class Adder[Q : IntQueue](m: Int, q: Q):
      private def add(): Int =
         val n = FastRandom.nextInt()
         q.put(n)
         n

      def run(): Int =
         var sum = 0
         m times (sum ^= add())
         sum
   end Adder

   private class Taker[Q : IntQueue](m: Int, q: Q):
      def run(): Int =
         var sum, count = 0
         while count < m do
            for value <- q.take() do
               sum ^= value
               count += 1
         sum
   end Taker

   private def runTest[Q : IntQueue](queue: Q, a: Int, t: Int, m: Int, r: Int) =
      require(m % a == 0 && m % t == 0)
      val ma = m / a
      val mt = m / t
      withThreadsAndWait(r) {
         val start = CountDownLatch(r min (a + t))
         val adders = Future.traverse(1 to a) { _ =>
            Future {
               val adder = Adder(ma, queue)
               start.countDownAndWait()
               adder.run()
            }
         }
         val takers = Future.traverse(1 to t) { _ =>
            Future {
               val taker = Taker(mt, queue)
               start.countDownAndWait()
               taker.run()
            }
         }
         for adds <- adders; takes <- takers yield
            val added = adds.foldLeft(0)(_ ^ _)
            val taken = takes.foldLeft(0)(_ ^ _)
            assert(taken == added)
      }

   for (m, a, t, r) <- Seq(
        (1000, 10, 10, 20),
        (10_000, 10, 10, 20),
        (100_000, 10, 10, 20),
        (200_000, 20, 20, 24),
        (200_000, 20, 20, 64),
        (400_000, 40, 10, 24),
        (400_000, 40, 10, 64),
        (400_000, 10, 40, 24),
        (400_000, 10, 40, 64)
      )
   do
      test(s"singlepublic: $a adders, $t takers, $m messages, $r threads") {
         runTest(singlepublic.ConcurrentQueue[Int](), a, t, m, r)
      }
      test(s"singleprivate: $a adders, $t takers, $m messages, $r threads") {
         runTest(singleprivate.ConcurrentQueue[Int](), a, t, m, r)
      }
      test(s"split: $a adders, $t takers, $m messages, $r threads") {
         runTest(split.ConcurrentQueue[Int](), a, t, m, r)
      }
