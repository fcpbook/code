package chap01

import chap01.Section_2J.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

import java.util

class Section_2_Tests extends AnyFunSuite:
   test("firstString") {
      val list = util.ArrayList(util.List.of(A, B))
      assert(firstString1(list) == A)
      assert(list == util.List.of(A, B))
      assert(firstString2(list) == A)
      assert(list == util.List.of(B))
   }
