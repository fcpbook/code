package chap02

import chap02.Section_6.*
import org.scalatest.funsuite.AnyFunSuite

class Section_6_Tests extends AnyFunSuite:
   test("Abs1") {
      assert(Abs1.abs(42) == 42)
      assert(Abs1.abs(-42) == 42)
   }

   test("Abs2") {
      assert(Abs2.abs(42) == 42)
      assert(Abs2.abs(-42) == 42)
   }
