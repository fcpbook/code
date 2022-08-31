package chap12

import chap12.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

import java.util

class Section_2_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == List(30, 63, 48, 84, 70))
      assert(demo2 == List(30, 63, 48, 84, 70))
      assert(Section_2J.demo() == util.Collections.nCopies(5, 30))
   }

   test("memo") {
      var calls = 0

      def f(x: Int) =
         calls += 1
         x + 1

      val g = memo(f)
      assert(g(1) == 2)
      assert(calls == 1)
      assert(g(2) == 3)
      assert(calls == 2)
      assert(g(1) == 2)
      assert(calls == 2)
      assert(g(2) == 3)
      assert(calls == 2)
   }
