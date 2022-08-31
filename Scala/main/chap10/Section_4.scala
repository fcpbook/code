package chap10

import mocks.Chap10.{ abc, f2 as f }
import tinyscalautils.text.StringLetters.X

import scala.annotation.tailrec

//noinspection SimplifiableFoldOrReduce
object Section_4:
   def demo1: String = abc.foldLeft(X)(f) // f(f(f(X,A),B),C)

   def demo2: String = abc.foldRight(X)(f) // f(A,f(B,f(C,X)))

   object Iter:
      def calculate(numbers: List[Int]): Double =
         var acc = 10.0
         for x <- numbers do acc = 3.0 * acc + x + 1.0
         acc

   object Rec:
      def calculate(numbers: List[Int]): Double =
         @tailrec
         def loop(nums: List[Int], acc: Double): Double = nums match
            case Nil    => acc
            case x :: r => loop(r, 3.0 * acc + x + 1.0)

         loop(numbers, 10.0)

   object FoldLeft:
      def calculate(numbers: List[Int]): Double =
         numbers.foldLeft(10.0)((acc, x) => 3.0 * acc + x + 1.0)

   def sum(list: List[Int]): Int = list.foldLeft(0)(_ + _)

   def product(list: List[Int]): Int = list.foldLeft(1)(_ * _)

   def reverse[A](list: List[A]): List[A] =
      list.foldLeft(List.empty[A])((l, x) => x :: l)

   def filter[A](list: List[A], test: A => Boolean): List[A] =
      list.foldRight(List.empty[A])((x, l) => if test(x) then x :: l else l)

   object Reduce:
      def demo: String = abc.reduceLeft(f) // f(f(A,B),C)

      def sum(list: List[Int]): Int = list.reduce(_ + _)

      def product(list: List[Int]): Int = list.reduce(_ * _)

   def demo3(lines: List[String]): Double =
      lines.map(_.toDouble.abs).filter(_ != 0.0).map(math.log).reduce(_ + _)

   def demo4(lines: List[String]): Double =
      lines.foldLeft(0.0) { (sum, line) =>
         val x = line.toDouble.abs
         if x != 0.0 then sum + math.log(x) else sum
      }
