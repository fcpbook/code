package chap06

import mocks.Chap06.merge
import tinyscalautils.text.StringLetters.*

import java.util.NoSuchElementException

object Section_3:
   // noinspection NoTailRecursionAnnotation
   // DON'T DO THIS!
   def last[A](list: List[A]): A = list match
      case Nil       => throw NoSuchElementException("last(empty)")
      case _ :: tail => last(tail)

   // DON'T DO THIS!
   def mergeSort[A](list: List[A]): List[A] =
      if list.isEmpty then list
      else
         val (left, right) = list.splitAt(list.length / 2) // split in the middle
         merge(mergeSort(left), mergeSort(right))

   def demo: (List[String], List[String]) = List(A, B, C, D).splitAt(2) // (List(A,B), List(C,D))

   def a(x: BigInt, y: BigInt): BigInt = (x, y) match
      case (0, _) => y + 1
      case (_, 0) => a(x - 1, 1)
      case _      => a(x - 1, a(x, y - 1))
