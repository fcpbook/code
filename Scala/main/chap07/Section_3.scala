package chap07

import chap05.Section_4.isEmpty
import tinyscalautils.text.StringLetters.*

import java.util.NoSuchElementException
import scala.annotation.tailrec

object Section_3:
   object DropIfThen:
      @tailrec
      def drop[A](list: List[A], n: Int): List[A] =
         if n == 0 then list
         else
            list match
               case Nil       => Nil
               case _ :: tail => drop(tail, n - 1)

      def demo: List[String] = drop(List(A, B, C, D), 2) // List(C, D)
   end DropIfThen

   @tailrec
   def drop[A](list: List[A], n: Int): List[A] = (list, n) match
      case (_, 0) | (Nil, _) => list
      case (_ :: tail, _)    => drop(tail, n - 1)

   def getAt[A](list: List[A], i: Int): A = drop(list, i) match
      case Nil        => throw NoSuchElementException("getAt(empty)")
      case value :: _ => value
