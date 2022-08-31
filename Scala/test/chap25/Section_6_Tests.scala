package chap25

import chap25.Section_6.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.lang.unit
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.*
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.{ delay, timeIt, timeOf }
import tinyscalautils.util.FastRandom

import java.util.concurrent.ConcurrentLinkedQueue
import scala.concurrent.Future
import scala.jdk.CollectionConverters.IterableHasAsScala

class Section_6_Tests extends AnyFunSuite with Tolerance:
   test("flaw") {
      def f(n: Int): Unit = delay(n)(unit)
      val g               = MemoBad.memo(f)
      withThreadPoolAndWait(Executors.newTimer(4), shutdown = true) {
         val f1 = Future(timeOf(g(10)))
         val f2 = DelayedFuture(9.0)(timeOf(g(10)))
         for time1 <- f1; time2 <- f2 yield
            assert(time1 === 10.0 +- 0.1)
            assert(time2 === 10.0 +- 0.1)
      }
   }

   test("no flaw") {
      def f(n: Int): Thread = delay(n)(Thread.currentThread)
      val g                 = memo(f)
      withThreadPoolAndWait(Executors.newTimer(4), shutdown = true) {
         val f1 = Future(timeIt(g(10)))
         val f2 = DelayedFuture(9.0)(timeIt(g(10)))
         for (thread1, time1) <- f1; (thread2, time2) <- f2 yield
            assert(time1 === 10.0 +- 0.1)
            assert(time2 === 1.0 +- 0.1)
            assert(thread1 eq thread2)
      }
   }

   test("plenty") {
      val m = 1_000_000
      val n = availableProcessors
      object f extends (Int => Int):
         val inputs = ConcurrentLinkedQueue[Int]()
         def apply(x: Int): Int =
            inputs.add(x)
            x
      val g = memo(f)
      syncForkJoin(1 to n) { _ =>
         m times {
            val x = FastRandom.nextInt(m)
            assert(g(x) == x)
         }
      }
      val nums = f.inputs.asScala
      assert(nums.toSet.size == nums.size)
   }
