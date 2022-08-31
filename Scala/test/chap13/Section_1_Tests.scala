package chap13

import chap13.Section_1.*
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("demos") {
      assert(Variant1.demo1 == List("two", "three", "four"))
      assert(Variant1.demo2 == List("two", "three", "four"))
      assert(Variant1.demo3 == List("one", "two"))
      assert(Variant1.demo4 == List("one", "two", "three", "four"))
      assert(Variant2.demo1 == List("two", "three", "four"))
      assert(Variant2.demo2 == List("two", "three", "four"))
      assertThrows[NullPointerException](Variant2.demo3)
      assertThrows[NullPointerException](Variant2.demo4)
   }
