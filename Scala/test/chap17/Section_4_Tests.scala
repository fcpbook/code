package chap17

import chap17.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import util.invokeAndWait

class Section_4_Tests extends AnyFunSuite:
   test("demo") {
      invokeAndWait(demo1(), waitForChildren = false)
   }

   test("demo2") {
      val lines = invokeAndWait(demo2(), waitForChildren = false)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.last == "main: END")
      assert(lines.contains("printerA: A"))
      assert(lines.contains("printerB: B"))
      assert(lines.contains("printerC: C"))
   }
