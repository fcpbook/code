package chap07

import chap05.Section_4.isEmpty
import chap07.Section_3.drop
import chap07.Section_4.take
import tinyscalautils.text.StringLetters.*

object Section_6:
   def demo: List[List[String]] = group(List(A, B, C, D, E), 2) // List(List(A,B), List(C,D), List(E))

   object GroupBad:
      // DON'T DO THIS!
      def group[A](list: List[A], k: Int): List[List[A]] = take(list, k) :: group(drop(list, k), k)

   def splitAt[A](list: List[A], n: Int): (List[A], List[A]) = (list, n) match
      case (_, 0) | (Nil, _) => (Nil, list)
      case (head :: tail, _) =>
         val (left, right) = splitAt(tail, n - 1)
         (head :: left, right)

   def group[A](list: List[A], k: Int): List[List[A]] =
      if isEmpty(list) then Nil
      else
         val (first, more) = splitAt(list, k)
         first :: group(more, k)
