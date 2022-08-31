package chap15

import chap15.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.X

class Section_4_Tests extends AnyFunSuite:
   private class PolyListList extends Polymorphic.ListADT:
      type List[A] = scala.collection.immutable.List[A]
      def empty[A]: List[A]                     = Nil
      def cons[A](x: A, list: List[A]): List[A] = x :: list
      def head[A](list: List[A]): A             = list.head
      def tail[A](list: List[A]): List[A]       = list.tail
      def length[A](list: List[A]): Int         = list.length

   private class MonoListList[A] extends Monomorphic.ListADT[A]:
      type List = scala.collection.immutable.List[A]
      val empty: List                  = Nil
      def cons(x: A, list: List): List = x :: list
      def head(list: List): A          = list.head
      def tail(list: List): List       = list.tail
      def length(list: List): Int      = list.length

   test("Polymorphic ListADT") {
      val module = PolyListList()
      import module.*
      val s1: List[String] = empty
      assert(length(s1) == 0)
      val s2 = cons(X, s1)
      assert(length(s2) == 1)
      assert(head(s2) == X)
      assert(tail(s2) == s1)
   }

   test("Monomorphic ListADT") {
      val module = MonoListList[String]()
      import module.*
      val s1: List = empty
      assert(length(s1) == 0)
      val s2 = cons(X, s1)
      assert(length(s2) == 1)
      assert(head(s2) == X)
      assert(tail(s2) == s1)
   }
