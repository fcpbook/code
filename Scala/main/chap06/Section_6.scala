package chap06

import chap06.Section_4.BinTree
import chap06.Section_4.BinTree.{ Empty, Node }

import java.util.NoSuchElementException
import scala.annotation.tailrec

object Section_6:
   object IterativeBinarySearch:
      def search(sortedSeq: IndexedSeq[String], target: String): Option[Int] =
         var from = 0
         var to   = sortedSeq.length - 1
         while from <= to do
            val middle = (from + to) / 2
            sortedSeq(middle) match
               case midVal if target > midVal => from = middle + 1
               case midVal if target < midVal => to = middle - 1
               case _ /* found at middle */   => return Some(middle)
         end while
         None

   def search(sortedSeq: IndexedSeq[String], target: String): Option[Int] =
      @tailrec
      def doSearch(from: Int, to: Int): Option[Int] =
         if from > to then None
         else
            val middle = (from + to) / 2
            sortedSeq(middle) match
               case midVal if target > midVal => doSearch(middle + 1, to)
               case midVal if target < midVal => doSearch(from, middle - 1)
               case _ /* found at middle */   => Some(middle)

      doSearch(0, sortedSeq.length - 1)
   end search

   @tailrec
   def last[A](list: List[A]): A = list match
      case Nil          => throw NoSuchElementException("last(empty)")
      case head :: tail => if tail.isEmpty then head else last(tail)

   def factorial(n: Int): Int =
      @tailrec
      def loop(m: Int, f: Int): Int = if m == 0 then f else loop(m - 1, m * f)

      loop(n, 1)
   end factorial

   object SizeAttempt:
      def size[A](tree: BinTree[A]): Int =
         def loop(tr: BinTree[A], sz: Int): Int = tr match
            case Empty                => sz
            case Node(_, left, right) => loop(right, loop(left, sz + 1))

         loop(tree, 0)
      end size
   end SizeAttempt

   def size[A](tree: BinTree[A]): Int =
      @tailrec
      def sizeSum(list: List[BinTree[A]], sum: Int): Int = list match
         case Nil                           => sum
         case Empty :: trees                => sizeSum(trees, sum)
         case Node(_, left, right) :: trees => sizeSum(left :: right :: trees, sum + 1)

      sizeSum(List(tree), 0)
   end size
