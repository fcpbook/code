package chap12

import mocks.Chap12.Data

import scala.annotation.tailrec

//noinspection TypeAnnotation
object Section_5:
   object SearchList:
      def searchData(data: List[Data]): Option[Data] = data.find(_.value > 10)

   object SearchByNameList:
      def searchData(data: => List[Data]): Option[Data] = data.find(_.value > 10)

   object SearchThunkList:
      def searchData(data: List[() => Data]): Option[Data] = data.map(_()).find(_.value > 10)

   object Variant1:
      @tailrec
      def searchData(data: LazyList[Data]): Option[Data] =
         if data.isEmpty then None
         else if data.head.value > 10 then Some(data.head)
         else searchData(data.tail)

   object Variant2:
      @tailrec
      def searchData(data: LazyList[Data]): Option[Data] = data match
         case LazyList()    => None
         case head #:: tail => if head.value > 10 then Some(head) else searchData(tail)

   def searchData(data: LazyList[Data]): Option[Data] = data.find(_.value > 10)

   object ListHanoi:
      def hanoi[A](n: Int, from: A, mid: A, to: A): List[(A, A)] =
         if n == 0 then List.empty
         else hanoi(n - 1, from, to, mid) ::: (from, to) :: hanoi(n - 1, mid, from, to)

   def hanoi[A](n: Int, from: A, mid: A, to: A): LazyList[(A, A)] =
      if n == 0 then LazyList.empty
      else hanoi(n - 1, from, to, mid) #::: (from, to) #:: hanoi(n - 1, mid, from, to)

   val allMoves    = hanoi(100, 'L', 'M', 'R')
   val oneMove     = allMoves(999) // ('M', 'L')
   val anotherMove = allMoves(49)  // ('L', 'R')
