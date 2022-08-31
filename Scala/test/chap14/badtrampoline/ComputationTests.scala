package chap14.badtrampoline

import org.scalatest.funsuite.AnyFunSuite

class ComputationTests extends AnyFunSuite:
   private def f(n: Int): Computation[Int] =
      if n == 0 then Done(n) else Call(() => f(n - 1)).map(_ + 1)

   test("small") {
      assert(f(10).result == 10)
   }

   test("big") {
      assertThrows[StackOverflowError](f(1_000_000).result)
   }
