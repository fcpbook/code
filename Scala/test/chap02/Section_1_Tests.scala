package chap02

import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("abs (Scala)") {
      assert(Section_1.abs(42) == 42)
      assert(Section_1.abs(-42) == 42)
   }

   test("abs (Java)") {
      assert(Section_1J.abs(42) == 42)
      assert(Section_1J.abs(-42) == 42)
   }
