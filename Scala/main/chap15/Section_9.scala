package chap15

import chap15.Section_6.{ Magazine, Publication }
import mocks.Chap15.{ orderedBook1, orderedBook2 }

import scala.collection.SortedSet
import scala.collection.mutable.Set
import scala.collection.parallel.CollectionConverters.seqIsParallelizable

//noinspection ReferenceMustBePrefixed,TypeAnnotation
object Section_9:
   object Nobound:
      def printTitles(pubs: Set[Publication]): Unit = for pub <- pubs do println(pub.title)

   def printTitles[A <: Publication](pubs: Set[A]): Unit = for pub <- pubs do println(pub.title)

   object Wildcard:
      def printTitles(pubs: Set[? <: Publication]): Unit = for pub <- pubs do println(pub.title)

   // def f(pubs: Set[? <: Publication]) = pubs += pubs.head // cannot be compiled
   def f[A <: Publication](pubs: Set[A]) = pubs += pubs.head // OK

   def addMagazine[A >: Magazine](pubs: Set[A]): Unit = pubs += Magazine("M", 1, 10)

   /*
   def printTitlesAndAddMagazine[A <: Publication](pubs: Set[A]): Unit =
      for pub <- pubs do println(pub.title)
      pubs += Magazine("M", 1, 10) // rejected by the compiler

   def printTitlesAndAddMagazine[A >: Magazine](pubs: Set[A]): Unit =
      for pub <- pubs do println(pub.title)
      pubs += Magazine("M", 1, 10) // rejected by the compiler
    */

   def printTitlesAndAddMagazine[A >: Magazine <: Publication](pubs: Set[A]): Unit =
      for pub <- pubs do println(pub.title)
      pubs += Magazine("M", 1, 10)

   def printTitlesInOrder[A <: Publication & Ordered[A]](pubs: Set[A]): Unit =
      for pub <- SortedSet.from(pubs) do println(pub.title)

   def runInParallel[A](tasks: List[() => A]): List[A] = tasks.par.map(_.apply()).toList

   object Demo:
      val bookPublishers: List[BookPublisher] = mocks.Chap15.publishers
      val pubs: List[Publication]             = runInParallel(bookPublishers) // OK
