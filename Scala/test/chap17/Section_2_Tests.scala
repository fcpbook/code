package chap17

import chap17.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in
import tinyscalautils.text.printout
import util.invokeAndWait

class Section_2_Tests extends AnyFunSuite:
   test("demo") {
      val lines = invokeAndWait(demo(), waitForChildren = true)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.contains("main: END"))
      assert(lines.contains("printerA: A"))
      assert(lines.contains("printerB: B"))
      assert(lines.contains("printerC: C"))
   }

   test("Java method reference") {
      assert(
        printout(invokeAndWait(Section_2J().demo(), waitForChildren = true))
           in Set("A\nB\n", "B\nA\n")
      )
   }
