package chap12

import mocks.Chap12.times10

object Section_9:
   def demo1: Option[Int] =
      val iter1 = List(1, 2, 3).iterator
      iter1.find(_ > 1) // Some(2)
      // iter1.next() // invalid call

   def demo2: Iterator[Int] =
      val iter2 = List(1, 2, 3).iterator
      iter2.map(_ + 1) // a new iterator
      // iter2.next() // invalid call

   def demo3(): Unit =
      val list = List(1, 2, 3).map(times10)
      println(list)
      println(list.head)
      println(list.last)
      println(list.head)

   def demo4(): Unit =
      val stream = LazyList(1, 2, 3).map(times10)
      println(stream)
      println(stream.head)
      println(stream.last)
      println(stream.head)

   def demo5(): Unit =
      val view = List(1, 2, 3).view.map(times10)
      println(view)
      println(view.head)
      println(view.last)
      println(view.head)
