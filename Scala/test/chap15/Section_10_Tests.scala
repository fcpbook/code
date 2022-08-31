package chap15

import chap15.Section_10.*
import mocks.Chap15.{ book1 as book, * }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_10_Tests extends AnyFunSuite:
   private val report = Report("R", 1)

   test("unrelated types") {
      assertDoesNotCompile("""Section_6.printTitle(Report("R", 1))""")
   }

   test("demos") {
      assertResult("Count to count: from Monte-Cristo to Mathias Sandorf\n") {
         printout(StructuralTypes.demo1())
      }
      assert(printout(StructuralTypes.demo2()) == "Comte de Monte-Cristo\n")
      assert(printout(ExplicitEvidence.demo(report)) == "R\n")
      assert(printout(ImplicitEvidence.demo(report)) == "R\n")
      assert(printout(demo1(report)) == "R\n")
      assert(printout(demo2(book)) == "A\n")
      assert(printout(demo3(magazine)) == "M\n")
      assert(printout(demo4()) == "Famous Counts\n")
      assert(demo5 == 1.7999999999999998)
      assert(demo6 == 0.0)
      assert(demo7 == 1.8)
      assert(demo8 == 0)
   }

   test("printTitlesInOrder") {
      assert(printout(printTitlesInOrder(Set(orderedBook1, orderedBook2))) == "B\nA\n")
      assert(printout(printTitlesInOrder(Set(comparableBook1, comparableBook2))) == "B\nA\n")
   }

   test("cannot print titles of persons") {
      assertTypeError("""printTitle(Noble("A", "T"))""")
   }

   test("double average") {
      import DoubleAverage.average
      assert(average(Seq.empty) == 0.0)
      assert(average(List(1.2, 2.4)) == 1.7999999999999998)
   }

   test("no imports average") {
      import Variant.average
      assert(average(Seq.empty[Double]) == 0.0)
      assert(average(List(1.2, 2.4)) == 1.7999999999999998)
   }
