package chap25

import chap25.Section_1.*
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("demo") {
      demo(b => assert(b))
   }
