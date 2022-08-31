package chap07

import chap07.Section_3.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_3_Tests extends AnyFunSuite:
   private val abc   = List(A, B, C)
   private val large = List.range(0, 1_000_000)

   test("demo") {
      assert(DropIfThen.demo == List(C, D))
   }

   for (drop, name) <- Seq((DropIfThen.drop, "drop (if-then)"), (Section_3.drop, "drop (match)")) do
      test(name) {
         for n <- 0 to 5 do
            assert(drop(Nil, n) == Nil.drop(n))
            assert(drop(abc, n) == abc.drop(n))
         assert(drop(large, 900_000) == large.drop(900_000))
      }

   test("getAt") {
      assertThrows[NoSuchElementException](getAt(Nil, 0))
      assertThrows[NoSuchElementException](getAt(Nil, 4))
      assertThrows[NoSuchElementException](getAt(abc, 4))
      for i <- abc.indices do assert(getAt(abc, i) == abc(i))
      assert(getAt(large, 900_000) == large(900_000))
   }
