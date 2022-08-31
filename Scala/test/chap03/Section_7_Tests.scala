package chap03

import chap03.Section_7.*
import org.scalatest.funsuite.AnyFunSuite

class Section_7_Tests extends AnyFunSuite:
   test("lists") {
      assert(a == List(1, 2, 3))
      assert(b == List(0, 1, 2, 3))
      assert(c == List(2, 3))
   }
