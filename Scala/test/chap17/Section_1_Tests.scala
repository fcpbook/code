package chap17

import chap17.Section_1.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout
import util.invokeAndWait

class Section_1_Tests extends AnyFunSuite:
   test("demo1") {
      val lines = printout(invokeAndWait(demo1(), waitForChildren = false)).linesIterator.toSeq
      assert(lines.length == 2)
      assert(lines.head.startsWith("main at ") && lines.head.endsWith(": START"))
      assert(lines.last.startsWith("main at ") && lines.last.endsWith(": END"))
   }

   test("demo2") {
      val lines = invokeAndWait(demo2(), waitForChildren = true)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.contains("main: END"))
      assert(lines.exists(line => line.startsWith("Thread-") && line.endsWith(": A")))
      assert(lines.exists(line => line.startsWith("Thread-") && line.endsWith(": B")))
      assert(lines.exists(line => line.startsWith("Thread-") && line.endsWith(": C")))
   }

   test("demo3") {
      val lines = invokeAndWait(demo3(), waitForChildren = true)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.contains("main: END"))
      assert(lines.contains("printerA: A"))
      assert(lines.contains("printerB: B"))
      assert(lines.contains("printerC: C"))
   }
