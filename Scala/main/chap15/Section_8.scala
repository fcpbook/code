package chap15

import chap15.Section_6.{ Book, Publication }
import mocks.Chap15.{ book1, book2 }

import java.util.logging.Logger
import scala.collection.mutable

//noinspection TypeAnnotation
object Section_8:
   def printTitles(pubs: List[Publication]) = for pub <- pubs do println(pub.title)

   def demo(): Unit =
      val books: List[Book] = List(book1, book2)
      printTitles(books) // prints both titles

   // noinspection TypeAnnotation
   object ArrayNonVariant:
      def printTitles(pubs: Array[Publication]) = for pub <- pubs do println(pub.title)

      val books: Array[Book] = Array(book1, book2)
   // printTitles(books) // rejected by the compiler

   def higherOrder(f: Book => Publication) = mocks.Chap15.higherOrder(f)

   class Ref[+A](contents: A):
      private var count = 0

      def get(): A =
         count += 1
         contents

      def accessCount: Int = count

      def reset(): Unit = count = 0
   end Ref

   class TrashCan[-A](log: Logger):
      def trash(x: A): Unit = log.info(s"trashing: $x")
