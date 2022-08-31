package chap18

import chap18.Section_5.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.PrintingMode
import util.invokeAndWait

class Section_5_Tests extends AnyFunSuite:
   private def testDemo(demo: PrintingMode ?=> Unit) =
      val lines = invokeAndWait(demo, waitForChildren = false)
      assert(lines.length == 3)
      assert(lines.head == "main: START")
      assert(lines.last == "main: END")
      val n = lines(1).substring(6).trim.toInt
      assert(n == 10)

   test("demo1") {
      testDemo(demo1())
   }

   test("demo2") {
      testDemo(demo2())
   }

   test("demo3") {
      testDemo(demo3())
   }
