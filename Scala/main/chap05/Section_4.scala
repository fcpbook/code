package chap05

import java.util.NoSuchElementException

object Section_4:
   def head[A](list: List[A]): A = list match
      case h :: _ => h
      case Nil    => throw NoSuchElementException("head(empty)")

   def tail[A](list: List[A]): List[A] = list match
      case _ :: t => t
      case Nil    => throw NoSuchElementException("tail(empty)")

   def isEmpty[A](list: List[A]): Boolean = list match
      case Nil => true
      case _   => false

   object IsEmptyVariant:
      def isEmpty[A](list: List[A]): Boolean = list match
         case _ :: _ => false
         case _      => true

   // noinspection OptionEqualsSome
   def demo: Boolean =
      (List(Some(("foo", 42)), None): @unchecked) match
         case (head @ Some(str, n)) :: tail =>
            head == Some(("foo", 42)) // the first option in the list
            && str == "foo"           // the first half of the pair in the first option
            && n == 42                // the second half of the pair in the first option
            && tail == List(None)     // the other options in the list
