package chap07

import chap07.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_4_Tests extends AnyFunSuite:
   private val abc   = List(A, B, C)
   private val large = List.range(0, 1_000_000)

   test("take") {
      for n <- 0 to 5 do
         assert(take(Nil, n) == Nil.take(n))
         assert(take(abc, n) == abc.take(n))
      assert(take(large, 10) == large.take(10))
   }
