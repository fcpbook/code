package chap03

import chap03.Section_1.*
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("format") {
      assert(demo1 == "> command")
      assert(demo2 == "% command")
   }

   test("uniqueName") {
      assert(demo3 == "user-1")
      assert(demo4 == "user-2")
   }
