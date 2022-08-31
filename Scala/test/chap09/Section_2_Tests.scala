package chap09

import chap09.Section_1.temps
import chap09.Section_2.*
import mocks.Chap09.{ Project, projects }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_2_Tests extends AnyFunSuite:
   test("demos") {
      assert(PlusNotCurried.demo == 8)
      assert(PlusCurried.demo1(3) == 8)
      assert(PlusCurried.demo2 == 8)
      assert(demo1)
      assert(demo2)
      assert(demo3)
      assert(printout(demo4()) == "4\n")
      assert(printout(demo5()) == "4\n")
      assert(demo6 == 8)
   }

   test("largerThan") {
      assert(temps.find(greaterThan(90)).contains(91))
   }

   test("hasID") {
      assert(projects.find(hasID(12345L)).contains(Project(12345)))
   }

   test("plus") {
      assert(PlusNotCurried.plus(5, 3) == 8)
      assert(PlusCurried.plus(5)(3) == 8)
   }
