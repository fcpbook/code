package chap05

import chap05.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("pair") {
      assert(demo1 == """str is the string "foo", n is the integer 42""")
      assert(Demo2.str == "foo")
      assert(Demo2.n == 42)
   }

   test("case classes") {
      import Demo3.*
      assert(demo1 == "Phoenix")
      assert(demo2 == 122)
      assert(name == "Phoenix")
      assert(temp == 122)
   }
