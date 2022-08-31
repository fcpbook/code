package chap10

import chap10.Section_6.*
import mocks.Chap09.{ date1, date2, date3 }
import org.scalatest.funsuite.AnyFunSuite

class Section_6_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == List(69, 70, 78, 88, 91, 98, 100))
      assert(demo2 == List(100, 98, 91, 88, 78, 70, 69))
      assert(demo3 == List("X", "Y", "ABC", "DEF"))
      assert(demo4 == List("X", "Y", "ABC", "DEF"))
      assert(demo5 == List(1, 1, 3, 3))
      assert(demo6 == List((2.3, 0.8), (3.2, 1.1)))
      assert(demo7 == 100)
      assert(demo8 == (date3, 88))
      assert(demo9 == (date2, 93))
      assert(demo10 == (date1, 93))
   }
