package chap02

import chap02.Section_8.*
import org.scalatest.funsuite.AnyFunSuite

class Section_8_Tests extends AnyFunSuite:
   test("formatMessage") {
      assert(demo1 == "hello\n")
      assert(demo2 == "Joe: hello\n")
      assert(demo3 == "Joe: hello")
   }
