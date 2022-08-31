package chap03

import chap03.Section_3.*
import chap03.Section_3J.demo
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in
import tinyscalautils.text.printout
import tinyscalautils.util.FastRandom

class Section_3_Tests extends AnyFunSuite:
   private def num: Int = FastRandom.between(1, 201)

   test("demos") {
      assert(printout(demo(num)) in Set("small\n", "large\n"))
      assert(demo1(num) in Set("small", "large"))
      assert(demo2(num) in Set("SMALL", "LARGE"))
      assert(demo3(num) in Set("the number is small", "the number is large"))
      assert(printout(demo4(num)) in Set("small\n", "large\n"))
      assert(printout(demo5(num)) in Set("small\n", "large\n"))
   }
