package chap09

import chap09.Section_5.*
import mocks.Chap09.{ endLogging, startLogging }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_5_Tests extends AnyFunSuite:
   test("demos") {
      assert(printout(demo(42)) == "42\n")
   }

   test("absComp") {
      assert(absComp.compare(2, -3) < 0)
      assert(absComp.compare(-3, 2) > 0)
   }

   test("loggingStream") {
      startLogging()
      loggingStream.close()
      assert(endLogging().contains("INFO: closing stream"))
   }
