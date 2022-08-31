package mocks

import scala.annotation.unused
import scala.collection.mutable

object Chap06:
   type MutableCollection[A]   = mutable.Set[A]
   type ImmutableCollection[A] = Set[A]

   var dump: mutable.Buffer[String] = mutable.Buffer.empty

   def mutableProcessOne[A](collection: MutableCollection[A]): Unit =
      dump += collection.head.toString
      collection -= collection.head

   def immutableProcessOne[A](collection: ImmutableCollection[A]): ImmutableCollection[A] =
      dump += collection.head.toString
      collection.tail

   // noinspection NotImplementedCode
   def merge[A](@unused l1: List[A], @unused l2: List[A]): List[A] = ???
