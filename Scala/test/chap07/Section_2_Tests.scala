package chap07

import chap07.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_2_Tests extends AnyFunSuite:
   private val abc   = List(A, B, C)
   private val large = List.range(0, 1_000_000)

   test("contains logical") {
      import ContainsLogical.contains
      assert(contains(Nil, X) == Nil.contains(X))
      for x <- X :: abc do assert(contains(abc, x) == abc.contains(x))
   }

   test("demo") {
      import ContainsLogical.demo
      for x <- X :: abc do assert(demo(abc, x) == abc.contains(x))
   }

   test("contains") {
      assert(contains(Nil, X) == Nil.contains(X))
      for x <- X :: abc do assert(contains(abc, x) == abc.contains(x))
   }

   test("length stack") {
      import LenStack.length

      assert(length(Nil) == 0)
      assert(length(abc) == 3)
      assertThrows[StackOverflowError](length(large))
   }

   test("length") {
      assert(length(Nil) == 0)
      assert(length(abc) == 3)
      assert(length(large) == large.length)
   }
