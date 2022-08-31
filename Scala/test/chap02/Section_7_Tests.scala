package chap02

import chap02.Section_7.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_7_Tests extends AnyFunSuite:
   test("printf") {
      assert(printout(demo1()) == "99 bottles of beer\n")
      assert(printout(demo2()) == "total: $12.35\n")
   }

   test("average") {
      assert(demo3 == (1.0 + 2.3 + 4.1) / 3)
      assert(demo4 == (10.0 + 20.0) / 2)
      assert(demo5 == 10.0)
   }
