package chap06

import scala.annotation.tailrec
import scala.collection.mutable

object Section_1:
   object MutableLoop:
      import mocks.Chap06.MutableCollection

      // processes one element and removes it
      def processOne[A](collection: MutableCollection[A]): Unit =
         mocks.Chap06.mutableProcessOne(collection)

      def processCollection[A](collection: MutableCollection[A]): Unit =
         while collection.nonEmpty do processOne(collection)
   end MutableLoop

   object ImmutableLoop:
      import mocks.Chap06.ImmutableCollection

      // processes one element, and returns a collection with this element removed
      def processOne[A](collection: ImmutableCollection[A]): ImmutableCollection[A] =
         mocks.Chap06.immutableProcessOne(collection)

      def processCollection[A](collection: ImmutableCollection[A]): Unit =
         var remaining = collection
         while remaining.nonEmpty do remaining = processOne(remaining)
   end ImmutableLoop

   object ImmutableRec:
      import ImmutableLoop.processOne
      import mocks.Chap06.ImmutableCollection

      // noinspection NoTailRecursionAnnotation
      def processCollection[A](collection: ImmutableCollection[A]): Unit =
         if collection.nonEmpty then processCollection(processOne(collection))
   end ImmutableRec
