package chap03

import chap03.Section_6.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_6_Tests extends AnyFunSuite:
   test("demos") {
      assert(Demo1.set == Set(A, B, C))
      assert(Demo2.set == Set(A, B, C))
      assert(Demo3.set1 == Set(1, 2, 3))
      assert(Demo3.set2 == Set(1, 2, 3))
      assert(Demo3.set3 == Set(1, 2, 3))
   }
