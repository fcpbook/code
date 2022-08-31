package chap14

import chap14.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo)
   }

   test("without implicits") {
      import WithoutImplicit.*
      for n <- 0 to 100 do
         assert(isEven(n).result == (n % 2 == 0))
         assert(isOdd(n).result == (n  % 2 != 0))
      assert(isEven(1_000_000).result)
   }

   test("with implicits") {
      for n <- 0 to 100 do
         assert(isEven(n).result == (n % 2 == 0))
         assert(isOdd(n).result == (n  % 2 != 0))
   }
