package chap15

import chap15.Section_6.*
import mocks.Chap15.book1
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_6_Tests extends AnyFunSuite:
   test("printTitles") {
      assert(printout(printTitle(book1)) == "A\n")
   }
