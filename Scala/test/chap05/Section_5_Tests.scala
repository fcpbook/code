package chap05

import chap05.Section_5.*
import org.scalatest.funsuite.AnyFunSuite

class Section_5_Tests extends AnyFunSuite:
   test("eval") {
      assert(eval(demo))
   }
