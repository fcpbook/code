package chap16

import chap16.Section_1.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.text.PrintAccumulator
import tinyscalautils.threads.{ withThreadsAndWait, withUnlimitedThreadsAndWait }
import tinyscalautils.timing.timeOf
import tinyscalautils.util.average

import scala.concurrent.Future

class Section_1_Tests extends AnyFunSuite:
   test("demo1") {
      given out: PrintAccumulator = PrintAccumulator()
      withUnlimitedThreadsAndWait(awaitTermination = true)(Future(demo1()))
      val lines = out.resetLines().mkString
      assert(lines == "A\nB\nC\n" || lines == "A\nC\nB\n")
   }

   test("demo2") {
      given out: PrintAccumulator = PrintAccumulator()
      withUnlimitedThreadsAndWait(awaitTermination = true)(Future(demo2()))
      assert(out.resetLines().mkString == "A\nB\nC\n")
   }

   test("demo3") {
      def timedRun(n: Int): Double =
         import tinyscalautils.text.silentMode
         timeOf {
            withThreadsAndWait(n, awaitTermination = true) {
               Future(demo3())
            }
         }
      val (t1, t2) = Seq.fill(20)((timedRun(1), timedRun(2))).unzip
      val (a1, a2) = (average(t1, ignoredPairs = 1), average(t2, ignoredPairs = 1))
      println(s"single thread: $a1")
      println(s"multiple threads: $a2")
      assert(a2 / a1 < 0.75)
   }
