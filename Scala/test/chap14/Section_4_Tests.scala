package chap14

import chap06.Section_4.BinTree
import chap06.Section_4.BinTree.{ Empty, Node }
import chap14.Section_4.*
import chap14.trampoline.Computation
import chap14.trampoline.Computation.call
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.{ L, M, R }

class Section_4_Tests extends AnyFunSuite:
   test("demos") {
      assertThrows[StackOverflowError](Overflow.demo)
      assert(demo.toString.length == 5_565_709)
   }

   test("factorial") {
      assert(factorial(0).result == 1)
      assert(factorial(1).result == 1)
      assert(factorial(10).result == 3628800)
      assert(Overflow.factorial(0) == 1)
      assert(Overflow.factorial(1) == 1)
      assert(Overflow.factorial(10) == 3628800)
      assert(WithForComp.factorial(0).result == 1)
      assert(WithForComp.factorial(1).result == 1)
      assert(WithForComp.factorial(10).result == 3628800)
      assert(WithForComp.factorial(1_000_000).result.toString.length == 5_565_709)
   }

   test("even/odd") {
      def isEven(n: Int): Computation[Boolean] =
         if n == 0 then true else call(isOdd(n - 1))

      def isOdd(n: Int): Computation[Boolean] =
         if n == 0 then false else call(isEven(n - 1))

      for n <- 0 to 100 do
         assert(isEven(n).result == (n % 2 == 0))
         assert(isOdd(n).result == (n  % 2 != 0))
      assert(isEven(1_000_000).result)
   }

   test("size") {
      var left, right: BinTree[Int] = Empty
      for i <- 1 to 1_000_000 do
         left = Node(i, left, Empty)
         right = Node(i, Empty, right)
      val tree = Node(0, left, right)
      assert(size(tree).result == 2_000_001)
      assert(WithForComp.size(tree).result == 2_000_001)
   }

   test("hanoi") {
      val moves = List((L, R), (L, M), (R, M), (L, R), (M, L), (M, R), (L, R))
      assert(hanoi(3, L, M, R).result == moves)
      assert(WithForComp.hanoi(3, L, M, R).result == moves)
   }
