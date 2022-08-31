package chap14

import chap06.Section_4.BinTree
import chap06.Section_4.BinTree.{ Empty, Node }
import chap14.trampoline.Computation
import chap14.trampoline.Computation.call

object Section_4:
   object Overflow:
      def factorial(n: Int): BigInt = if n == 0 then BigInt(1) else factorial(n - 1) * n

      def demo: BigInt = factorial(1_000_000) // throws StackOverflowError
   end Overflow

   def factorial(n: Int): Computation[BigInt] =
      if n == 0 then BigInt(1) else call(factorial(n - 1)).map(_ * n)

   def demo: BigInt = factorial(1_000_000).result // a number with 5,565,709 digits

   def size[A](tree: BinTree[A]): Computation[Int] = tree match
      case Empty => 0
      case Node(_, left, right) =>
         call(size(left)).flatMap(ls => call(size(right)).map(rs => 1 + ls + rs))

   def hanoi[A](n: Int, from: A, middle: A, to: A): Computation[List[(A, A)]] =
      if n == 0 then List.empty
      else
         val call1 = call(hanoi(n - 1, from, to, middle))
         val call2 = call(hanoi(n - 1, middle, from, to))
         call1.flatMap(moves1 => call2.map(moves2 => moves1 ::: (from, to) :: moves2))

   object WithForComp:
      def factorial(n: Int): Computation[BigInt] =
         if n == 0 then BigInt(1) else for f <- call(factorial(n - 1)) yield f * n

      def size[A](tree: BinTree[A]): Computation[Int] = tree match
         case Empty => 0
         case Node(_, left, right) =>
            for
               ls <- call(size(left))
               rs <- call(size(right))
            yield 1 + ls + rs

      def hanoi[A](n: Int, from: A, middle: A, to: A): Computation[List[(A, A)]] =
         if n == 0 then List.empty
         else
            for
               moves1 <- call(hanoi(n - 1, from, to, middle))
               moves2 <- call(hanoi(n - 1, middle, from, to))
            yield moves1 ::: (from, to) :: moves2
   end WithForComp
