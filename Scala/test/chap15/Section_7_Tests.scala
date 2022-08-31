package chap15

import chap15.Section_7.*
import mocks.Chap15.{ magazine, book1 as book }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_7_Tests extends AnyFunSuite:
   test("ad hoc") {
      assert(printout(displayBook(book)) == "A by a\n")
      assert(printout(displayMagazine(magazine)) == "M (2 pages)\n")
      assert(printout(display(book)) == "A by a\n")
      assert(printout(display(magazine)) == "M (2 pages)\n")
   }

   test("parametric") {
      assert(hashedBook == bookWithHash(book))
      assert(hashedMagazine == magazineWithHash(magazine))
   }

   test("printTitles") {
      assert(printout(printTitles(List(book, magazine))) == "A\nM\n")
   }

   test("subtype") {
      assert(printout(Bad.displayCollection(List(book, magazine))) == "A by a\nM (2 pages)\n")
      assertResult("T by A\nM (10 pages)\nReport: R\n") {
         import Good.*
         printout(demo(Book("T", "A", 10), Magazine("M", 1, 10), Report("R", 10)))
      }
   }
