package chap02

import chap02.Section_10.*
import org.scalatest.funsuite.AnyFunSuite

class Section_10_Tests extends AnyFunSuite:
   test("Untyped") {
      import Untyped.*
      assert(demo1 == 11)
      assert(demo2 == "EGG")
      assertTypeError("""first((1, 2)) + 10""")
      assertTypeError("""first(("egg", "chicken")).toUpperCase""")
   }

   test("TypedA") {
      import TypedA.*
      assert(demo1 == 1)
      assert(demo2 == 11)
      assert(demo3 == "egg")
      assert(demo4 == "EGG")
      assertTypeError("""first((1, "chicken")) + 10""")
   }

   test("TypedType") {
      import TypedType.*
      assert(first((1, 2)) == 1)
      assert(first(("egg", "chicken")) == "egg")
      assertTypeError("""first((1, "chicken")) + 10""")
   }

   test("First") {
      assert(demo1 == 11)
      assert(demo2 == "EGG")
   }
