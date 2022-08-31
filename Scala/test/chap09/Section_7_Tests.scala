package chap09

import chap09.Section_7.*
import mocks.Chap09.{ endLogging, startLogging }
import org.scalatest.funsuite.AnyFunSuite

class Section_7_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1)
      assert(!demo2)
      assert(demo3 == 3)
      assert(demo4 == 3)
      assert(demo6 == 1000)
   }

   test("logging") {
      startLogging()
      demo5()
      val str = endLogging()
      assert(str.contains("INFO: calling length (1) with foo"))
      assert(str.contains("INFO: calling length (2) with bar"))
      assert(str.contains("INFO: length(foo)=3"))
      assert(str.contains("INFO: length(bar)=3"))
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
