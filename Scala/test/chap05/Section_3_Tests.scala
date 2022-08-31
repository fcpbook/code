package chap05

import chap05.Section_3.*
import org.scalatest.funsuite.AnyFunSuite

class Section_3_Tests extends AnyFunSuite:
   test("options") {
      assert(someNum.contains(42))
      assert(noNum.isEmpty)
   }

   test("option 1") {
      assert(demo1(Some(42)) == 42)
      assert(demo1(Some(-42)) == 0)
      assert(demo1(None) == 0)
   }

   test("option 2") {
      assert(demo2(Some(42)) == 42)
      assert(demo2(Some(-42)) == 0)
      assert(demo2(None) == 0)
   }

   test("option 3") {
      assert(demo3(Some(42)) == 42)
      assert(demo3(Some(-42)) == 0)
      assert(demo3(None) == 0)
   }

   test("option 4") {
      assert(demo4(Some(42)) == 0)
      assert(demo4(Some(-42)) == 0)
      assert(demo4(None) == 0)
   }
