package chap07

import chap07.Section_6.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_6_Tests extends AnyFunSuite:
   private val abc = List(A, B, C)

   test("demo") {
      assert(demo == List(List(A, B), List(C, D), List(E)))
   }

   test("group, bad") {
      assertThrows[StackOverflowError](GroupBad.group(abc, 2))
   }

   test("group") {
      for n <- 1 to 4 do
         assert(group(Nil, n) == Nil.grouped(n).toList)
         assert(group(abc, n) == abc.grouped(n).toList)
   }

   test("splitAt") {
      for n <- 0 to 4 do
         assert(splitAt(Nil, n) == Nil.splitAt(n))
         assert(splitAt(abc, n) == abc.splitAt(n))
   }
