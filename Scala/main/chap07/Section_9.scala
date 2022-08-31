package chap07

import chap05.Section_4.{ head, tail }
import chap07.Section_5.append
import chap07.Section_7.reverse

import scala.annotation.tailrec
import scala.collection.mutable

object Section_9:
   object TakeReverse:
      def take[A](list: List[A], n: Int): List[A] =
         @tailrec
         def takeAndAdd(list: List[A], n: Int, added: List[A]): List[A] = (list, n) match
            case (_, 0) | (Nil, _) => added
            case (head :: tail, _) => takeAndAdd(tail, n - 1, head :: added)

         reverse(takeAndAdd(list, n, Nil))
      end take
   end TakeReverse

   object TakeBuffer:
      def take[A](list: List[A], n: Int): List[A] =
         @tailrec
         def takeAndAdd(list: List[A], n: Int, added: mutable.ListBuffer[A]): List[A] =
            (list, n) match
               case (_, 0) | (Nil, _) => added.result()
               case (head :: tail, _) => takeAndAdd(tail, n - 1, added += head)

         takeAndAdd(list, n, mutable.ListBuffer.empty[A])
      end take
   end TakeBuffer

   object TakeLoop:
      def take[A](list: List[A], n: Int): List[A] =
         val added = mutable.ListBuffer.empty[A]
         var elems = list
         var rem   = n
         while rem > 0 && elems.nonEmpty do
            added += head(elems)
            elems = tail(elems)
            rem -= 1
         added.result()
   end TakeLoop
