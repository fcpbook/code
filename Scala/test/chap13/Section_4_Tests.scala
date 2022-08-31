package chap13

import chap13.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == Right(List("two", "three")))
      assert(demo2 == Left("not found: words"))
      assert(demo3 == Right(42))
      assert(demo4 == Left("no number"))
      assert(demo5 == "(-42)")
      assert(demo6 == "no number")
   }
