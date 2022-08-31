package chap05

import chap05.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("head") {
      assert(head(List(1, 2, 3)) == 1)
      assertThrows[NoSuchElementException](head(Nil))
   }

   test("tail") {
      assert(tail(List(1, 2, 3)) == List(2, 3))
      assertThrows[NoSuchElementException](tail(Nil))
   }

   test("IsEmpty") {
      assert(isEmpty(Nil))
      assert(!isEmpty(List(Nil)))
      assert(IsEmptyVariant.isEmpty(Nil))
      assert(!IsEmptyVariant.isEmpty(List(Nil)))
   }

   test("nested") {
      assert(demo)
   }
