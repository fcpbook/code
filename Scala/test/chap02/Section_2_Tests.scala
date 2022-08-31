package chap02

import chap02.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("dots/abs") {
      assert(Set(demo1, demo2, demo3, demo4) == Set("..."))
   }
