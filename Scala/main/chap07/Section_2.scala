package chap07

import chap05.Section_4.{ head, isEmpty, tail }

import scala.annotation.tailrec

object Section_2:
   object ContainsLogical:
      @tailrec
      def contains[A](list: List[A], target: A): Boolean =
         !isEmpty(list) && (head(list) == target || contains(tail(list), target))

      // noinspection ScalaUnnecessaryParentheses
      def demo[A](list: List[A], target: A): Boolean =
         (if head(list) == target then true else contains(tail(list), target))
   end ContainsLogical

   @tailrec
   def contains[A](list: List[A], target: A): Boolean = list match
      case Nil          => false
      case head :: tail => head == target || contains(tail, target)

   object LenStack:
      def length[A](list: List[A]): Int = list match
         case Nil       => 0
         case _ :: tail => 1 + length(tail)
   end LenStack

   def length[A](list: List[A]): Int =
      @tailrec
      def addLength(theList: List[A], len: Int): Int = theList match
         case Nil       => len
         case _ :: tail => addLength(tail, len + 1)

      addLength(list, 0)
   end length
