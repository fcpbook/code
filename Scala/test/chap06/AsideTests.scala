package chap06

import chap06.Aside.*
import org.scalatest.funsuite.AnyFunSuite

class AsideTests extends AnyFunSuite:
   test("zero") {
      val test = NoTailRec()
      assert(test.zero(100) == 0)
      assertThrows[StackOverflowError](test.zero(1_000_000))
   }

   test("zero, tailrec") {
      val test = TailRec()
      assert(test.zero(100) == 0)
      assert(test.zero(1_000_000) == 0)
   }
