package chap02

import chap02.Section_9.*
import org.scalatest.funsuite.AnyFunSuite

class Section_9_Tests extends AnyFunSuite:
   test("formatMessage compile error") {
      assertTypeError("""formatMessage("hello", false)""")
   }

   test("formatMessage") {
      assert(demo1 == "hello")
      assert(demo2 == "Joe: hello\n")
      assert(demo3 == "Joe: hello\n")
      assert(demo4 == "Joe: hello")

      assert(demo5 == "Tweedledum: Tweedledee\n")
      assert(demo6 == "Tweedledum: Tweedledee\n")
   }

   test("Writer") {
      assert(demo7.autoflush)
      assert(demo8.autoflush)
   }
