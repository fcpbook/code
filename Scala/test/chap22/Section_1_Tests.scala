package chap22

import chap22.Section_1.*
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("demos") {
      demo1(b => assert(b))
      demo4(b => assert(b))
      demo5(b => assert(b))
   }
