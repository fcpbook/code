package chap10

import chap10.Section_5.*
import org.scalatest.funsuite.AnyFunSuite

class Section_5_Tests extends AnyFunSuite:
   test("demo1") {
      assert(demo1(4, mocks.Chap10.f1) == List("f(0)", "f(1)", "f(2)", "f(3)"))
   }

   test("demo2") {
      assert(demo2 == List("", "X", "XX", "XXX", "XXXX"))
   }

   test("demo3") {
      assert(demo3(4, mocks.Chap10.f1) == List("X", "f(X)", "f(f(X))", "f(f(f(X)))"))
   }

   test("demo4") {
      assert(demo4 == List("", "X", "XX", "XXX", "XXXX"))
   }

   test("demo5") {
      var n = 4

      def f(x: String): Option[(String, String)] =
         if n > 0 then
            n -= 1
            Some((s"fVal($x)", s"fNext($x)"))
         else None

      assert(
        demo5(f) == List(
          "fVal(X)",
          "fVal(fNext(X))",
          "fVal(fNext(fNext(X)))",
          "fVal(fNext(fNext(fNext(X))))"
        )
      )
   }

   test("demo6") {
      assert(demo6 == List("XXXX", "XXX", "XX", "X"))
   }
