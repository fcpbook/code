package chap14

import chap14.Section_1.*
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == 0)
      assert(demo2)
      assert(!demo3)
      assertThrows[StackOverflowError](demo4)
   }

   test("zero") {
      for n <- 0 to 100 do assert(zero(n) == 0)
   }

   test("even/odd") {
      for n <- 0 to 100 do
         assert(isEven(n) == (n % 2 == 0))
         assert(isOdd(n) == (n  % 2 != 0))
   }
