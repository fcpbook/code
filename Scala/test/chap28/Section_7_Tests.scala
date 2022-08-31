package chap28

import chap28.Section_7.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.*
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.{ sleep, timeIt, timeOf, zipWithDuration }

class Section_7_Tests extends AnyFunSuite with Tolerance:
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

   test("cached") {
      withThreadPoolAndWait(TimerContext(1), shutdown = true) {
         val runner = Runner(Seq(S1, S2, S3), global)
         val f1     = runner.compute('A', 1.0).zipWithDuration
         val f2     = runner.compute('B', 1.0).zipWithDuration
         sleep(1.5)
         val f3 = runner.compute('A', 5.0).zipWithDuration
         val f4 = runner.compute('B', 5.0).zipWithDuration
         sleep(1.0)
         val f5 = runner.compute('A', 2.0).zipWithDuration
         val f6 = runner.compute('A', 8.0).zipWithDuration
         val f7 = runner.compute('B', 0.5).zipWithDuration
         val f8 = runner.compute('B', 2.0).zipWithDuration
         val f9 = runner.compute('B', 8.0).zipWithDuration
         sleep(2.5)
         val f10 = runner.compute('A', 2.0).zipWithDuration
         val f11 = runner.compute('B', 7.0).zipWithDuration
         val f12 = runner.compute('B', 9.0).zipWithDuration
         for {
            (result1, time1)   <- f1
            (result2, time2)   <- f2
            (result3, time3)   <- f3
            (result4, time4)   <- f4
            (result5, time5)   <- f5
            (result6, time6)   <- f6
            (result7, time7)   <- f7
            (result8, time8)   <- f8
            (result9, time9)   <- f9
            (result10, time10) <- f10
            (result11, time11) <- f11
            (result12, time12) <- f12
         } yield
            assert(time1 === 1.0 +- 0.1)
            assert(result1.isEmpty)
            assert(time2 === 1.0 +- 0.1)
            assert(result2.isEmpty)
            assert(time3 === 2.0 +- 0.1)
            assert(result3.contains('A'))
            assert(time4 === 2.0 +- 0.1)
            assert(result4.isEmpty)
            assert(time5 === 1.0 +- 0.1)
            assert(result5.contains('A'))
            assert(time6 === 1.0 +- 0.1)
            assert(result6.contains('A'))
            assert(time7 === 0.5 +- 0.1)
            assert(result7.isEmpty)
            assert(time8 === 1.0 +- 0.1)
            assert(result8.isEmpty)
            assert(time9 === 2.0 +- 0.1)
            assert(result9.isEmpty)
            assert(time10 === 0.0 +- 0.1)
            assert(result10.contains('A'))
            assert(time11 === 0.0 +- 0.1)
            assert(result11.isEmpty)
            assert(time12 === 2.0 +- 0.1)
            assert(result12.isEmpty)
      }
   }
