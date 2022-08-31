package chap03

import chap03.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

import java.util
import scala.collection.mutable

class Section_2_Tests extends AnyFunSuite:
   test("impure add function") {
      import Section_2J.*
      val set = util.HashSet[String]()
      assert(demo(set))
      assert(!demo(set))
   }

   test("demo") {
      assert(demo(mutable.Set(A, Y)) == Set(A, X))
   }

   test("firstString") {
      val list = util.ArrayList(util.List.of(A, B, C))
      assert(getFirstString(list) == A)
      assert(list == util.List.of(A, B, C))
      assert(getAndRemoveFirstString(list) == A)
      assert(list == util.List.of(B, C))
      removeFirstString(list)
      assert(list == util.List.of(C))
   }
