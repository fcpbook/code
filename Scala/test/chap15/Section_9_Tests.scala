package chap15

import chap15.Section_6.{ Book, Magazine, Publication }
import chap15.Section_9.*
import mocks.Chap15.{ book1 as book, * }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

import java.util
import scala.collection.mutable
import scala.jdk.CollectionConverters.MutableSetHasAsJava

class Section_9_Tests extends AnyFunSuite:
   Magazine("A", 0, 0) // to keep imports

   private def books        = mutable.Set(book)
   private def pubs         = mutable.Set[Publication](book)
   private def orderedBooks = mutable.Set(orderedBook1, orderedBook2)

   test("printTitles, no bounds") {
      import Nobound.printTitles
      assert(printout(printTitles(pubs)) == "A\n")
      assertTypeError("printTitles(books)")
   }

   test("printTitles") {
      assert(printout(printTitles(pubs)) == "A\n")
      assert(printout(printTitles(books)) == "A\n")
   }

   test("printTitles, wildcards") {
      import Wildcard.printTitles
      assert(printout(printTitles(pubs)) == "A\n")
      assert(printout(printTitles(books)) == "A\n")
   }

   test("help inference") {
      import mutable.Set
      // noinspection ReferenceMustBePrefixed
      Set.empty // to keep import
      assertCompiles("def f[A <: Publication](pubs: Set[A]) = pubs += pubs.head")
      assertTypeError("def f(pubs: Set[? <: Publication]) = pubs += pubs.head")
   }

   test("addMagazine") {
      val set = pubs
      addMagazine(set)
      assert(set.size == 2)
   }

   test("printTitlesAndAddMagazine incorrect") {
      assertTypeError("""import scala.collection.mutable.Set
                        |def printTitlesAndAddMagazine[A <: Publication](pubs: Set[A]): Unit =
                        |   for pub <- pubs do println(pub.title)
                        |   pubs += Magazine("M", 1, 10)
                        |""".stripMargin)
      assertTypeError("""import scala.collection.mutable.Set
                        |def printTitlesAndAddMagazine[A >: Magazine](pubs: Set[A]): Unit =
                        |   for pub <- pubs do println(pub.title)
                        |   pubs += Magazine("M", 1, 10)
                        |""".stripMargin)
   }

   test("printTitlesAndAddMagazine") {
      val set = pubs
      assert(printout(printTitlesAndAddMagazine(set)) == "A\n")
      assert(set.size == 2)
   }

   test("printTitlesInOrder") {
      assert(printout(printTitlesInOrder(orderedBooks)) == "B\nA\n")
   }

   test("printTitles, Java") {
      import Section_9J.printTitles
      assert(printout(includeSystem = true)(printTitles(pubs.asJava)) == "A\n")
      assert(printout(includeSystem = true)(printTitles(books.asJava)) == "A\n")
   }

   test("addMagazine, Java") {
      import Section_9J.addMagazine
      val set = pubs.asJava
      addMagazine(set)
      assert(set.size == 2)
   }

   test("printTitlesInOrder, Java") {
      import Section_9J.printTitlesInOrder
      assert(printout(includeSystem = true)(printTitlesInOrder(orderedBooks.asJava)) == "B\nA\n")
   }

   test("runInParallel, Java, non variant") {
      import Section_9J.NonVariant.runInParallel
      val books = runInParallel(publishers2)
      assert(books == util.List.of(book))
      assertTypeError("runInParallel(publishers1)")
   }

   test("runInParallel, Java, semi-variant") {
      import Section_9J.SemiVariant.runInParallel
      val books: util.List[Book] = runInParallel(publishers1)
      assert(books == util.List.of(book))
   }

   test("runInParallel, Java") {
      import Section_9J.runInParallel
      val books = runInParallel(publishers1)
      assert(books == util.List.of(book))
   }

   test("runInParallel") {
      assert(Demo.pubs == List(book))
   }
