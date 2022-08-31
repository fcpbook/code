package chap28

import chap28.Section_5.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{
   Executors,
   availableProcessors,
   shutdownAndWait,
   withThreadPoolAndWait
}
import tinyscalautils.timing.{ delay, timeIt, timeOf, zipWithDuration }
import tinyscalautils.util.FastRandom

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.Future
import scala.concurrent.duration.SECONDS

class Section_5_Tests extends AnyFunSuite with Tolerance:
   test("no timeout") {
      withThreadPoolAndWait(TimerContext(availableProcessors.max(2)), shutdown = true) {
         val n = AtomicInteger(0)
         Future(delay(2.0)(0))
            .completeOnTimeout(5, SECONDS)(n.incrementAndGet())
            .zipWithDuration
            .map { (value, time) =>
               assert(value == 0)
               assert(n.get == 0)
               assert(time === 2.0 +- 0.1)
            }
      }
   }

   test("timeout") {
      withThreadPoolAndWait(TimerContext(availableProcessors.max(2)), shutdown = true) {
         val n = AtomicInteger(0)
         Future(delay(3.0)(0))
            .completeOnTimeout(2, SECONDS)(n.incrementAndGet())
            .zipWithDuration
            .map { (value, time) =>
               assert(value == 1)
               assert(n.get == 1)
               assert(time === 2.0 +- 0.1)
            }
      }
   }

   test("empty") {
      withThreadPoolAndWait(global) {
         findFirst(Seq.empty[Future[Int]], _ => true).map(_.isEmpty)
      }
   }

   test("small") {
      withThreadPoolAndWait(global) {
         val f1 = Future(delay(1)("A1"))
         val f2 = Future(delay(2)("B2"))
         val f3 = Future(delay(3)("B3"))
         findFirst(Seq(f1, f2, f3), _.startsWith("B")).zipWithDuration
            .flatMap { (opt, time) =>
               assert(opt.contains("B2"))
               assert(time === 2.0 +- 0.1)
               Future.sequence(Seq(f1, f2, f3))
            }
      }
   }

   for n <- Seq(10, 50, 100, 200, 500) do
      test(s"large (n = $n)") {
         withThreadPoolAndWait(global) {
            val futures =
               val fast = Seq.fill(n / 2)(FastRandom.between(0.0f, 1.5f) -> 1)
               val slow = Seq.fill(n / 2)(FastRandom.between(2.5f, 4.0f) -> 2)
               val all  = FastRandom.shuffle(((2.0f -> 3) +: slow) ++ fast)
               all.map((d, s) => Future(delay(d)(s)))
            findFirst(futures, _ > 1).zipWithDuration.flatMap { (opt, time) =>
               assert(opt.contains(3))
               assert(time === 2.0 +- 0.1)
               Future.sequence(futures)
            }
         }
      }

   test("success") {
      val time = timeOf {
         withThreadPoolAndWait(TimerContext(1), shutdown = true) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('A', 2.5))
            assert(shortTime === 0.0 +- 0.1)
            future.map(result => assert(result.contains('A')))
         }
      }
      assert(time === 2.0 +- 0.1)
   }

   test("failure") {
      val time = timeOf {
         withThreadPoolAndWait(TimerContext(1), shutdown = true) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('B', 10.0))
            assert(shortTime === 0.0 +- 0.1)
            future.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 2.0 +- 0.1)
   }

   test("timeout 1") {
      val time = timeOf {
         withThreadPoolAndWait(TimerContext(1), shutdown = true) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('A', 1.0))
            assert(shortTime === 0.0 +- 0.1)
            future.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 1.0 +- 0.1)
   }

   test("timeout 2") {
      val exec = Executors.newThreadPool(availableProcessors)
      val time = timeOf {
         withThreadPoolAndWait(TimerContext(1), shutdown = true) {
            val runner              = Runner(Seq.fill(100)(S3), exec)
            val (future, shortTime) = timeIt(runner.compute('B', 1.0))
            assert(shortTime === 0.0 +- 0.1)
            future.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 1.0 +- 0.1)
      assert(exec.shutdownAndWait(0.1))
   }
