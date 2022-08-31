package chap07

import tinyscalautils.text.StringLetters.*

object Section_5:
   def demo1: List[String] =
      val abc = List(A, B, C)
      concat(abc, abc) // List(A, B, C, A, B, C); standard in Scala as abc ::: abc

   def concat[A](list1: List[A], list2: List[A]): List[A] = list1 match
      case Nil            => list2
      case head1 :: tail1 => head1 :: concat(tail1, list2)

   def append[A](list: List[A], value: A): List[A] = concat(list, List(value))

   def demo2: List[Int] = flatten(List(List(1, 2, 3), Nil, List(4, 5), List(6))) // List(1, 2, 3, 4, 5, 6)
   def demo3: List[Int] = List(1, 2, 3) ::: Nil ::: List(4, 5) ::: List(6) // List(1, 2, 3, 4, 5, 6)

   def flatten[A](list: List[List[A]]): List[A] = list match
      case Nil          => Nil
      case head :: tail => concat(head, flatten(tail))

   def demo4: List[(String, Int)] = zip(List(A, B, C), List(1, 2, 3)) // List((A,1), (B,2), (C,3))

   def zip[A, B](list1: List[A], list2: List[B]): List[(A, B)] = (list1, list2) match
      case (Nil, _) | (_, Nil)              => Nil
      case (head1 :: tail1, head2 :: tail2) => (head1, head2) :: zip(tail1, tail2)
