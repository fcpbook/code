package chap22

import chap22.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("demo") {
      demo(b => assert(b))
   }
