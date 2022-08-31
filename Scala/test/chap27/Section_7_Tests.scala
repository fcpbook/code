package chap27

import chap27.Section_7.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.sleep

class Section_7_Tests extends AnyFunSuite:
   test("demo") {
      val lines = printout {
         val future = Demo().future1
         sleep(10.0)
         assert(!future.isCompleted)
      }.linesIterator.toIndexedSeq
      assert(lines.length == 4)
      assert(lines(0) == "START")
      assert(lines.contains("END"))
      val i1 = lines.indexOf("future 1 starts")
      val i2 = lines.indexOf("future 2 starts")
      assert(i1 > 0 && i2 > i1)
   }
