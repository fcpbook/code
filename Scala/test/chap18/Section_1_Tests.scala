package chap18

import chap18.Section_1.*
import org.scalatest.concurrent.{ Signaler, ThreadSignaler, TimeLimits }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.text.PrintAccumulator
import util.invokeAndWait

import scala.annotation.tailrec
import scala.concurrent.duration.DurationInt

class Section_1_Tests extends AnyFunSuite with TimeLimits:
   test("demo") {
      val lines = invokeAndWait(demo(), waitForChildren = false)
      assert(lines.length == 3)
      assert(lines.head == "main: START")
      assert(lines.last == "main: END")
      val n = lines(1).substring(6).toInt
      assert(n >= 0 && n <= 10)
   }

   test("count") {
      given out: PrintAccumulator = PrintAccumulator()
      @tailrec
      def runDemo(count: Int): Int =
         demo()
         if out.resetLines()(1) == "10\n" then runDemo(count + 1) else count

      given Signaler = ThreadSignaler
      cancelAfter(1.minute)(println(runDemo(1)))
   }
