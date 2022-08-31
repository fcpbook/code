package chap25

import chap25.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("demos") {
      demo1(b => assert(b))
      demo2(b => assert(b))
   }
