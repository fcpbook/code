package chap07

import chap07.Section_9.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_9_Tests extends AnyFunSuite:
   private val abc   = List(A, B, C)
   private val large = List.range(0, 1_000_000)

   for (take, name) <- Seq(
        (TakeReverse.take, "TakeReverse"),
        (TakeBuffer.take, "TakeBuffer"),
        (TakeLoop.take, "TakeLoop")
      )
   do
      test(name) {
         for n <- (0 to 5) ++ Seq(100_000, 900_000, 1_000_000, 10_000_000) do
            assert(take(Nil, n) == Nil.take(n))
            assert(take(abc, n) == abc.take(n))
            assert(take(large, n) == large.take(n))
      }
