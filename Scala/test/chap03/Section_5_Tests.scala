package chap03

import chap03.Section_5.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_5_Tests extends AnyFunSuite:
   test("demos") {
      assert(Demo1.set == Set(A, B, C))
      assert(Demo2.set == Set(A, B, C))
      assert(Demo3.set == Set(A, B))
   }
