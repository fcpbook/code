package chap15

import chap15.Section_6.{ Book, Magazine, Publication }
import chap15.Section_8.*
import mocks.Chap15.{ book1, book2, magazine }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

import java.util
import java.util.logging.Logger

class Section_8_Tests extends AnyFunSuite:
   test("Java") {
      import Section_8J.*
      assert(printout(includeSystem = true)(demo1(book1, magazine)) == "A\nM\n")
      val col1 = util.LinkedList(util.List.of(book1, magazine))
      assert(printout(includeSystem = true)(printTitlesAndAddMagazine(col1)) == "A\nM\n")
      assertTypeError("""|val books = util.List.of(book1, book2)
                         |printTitles(books)
                         |""".stripMargin)
      assertThrows[ArrayStoreException](printout(includeSystem = true)(Section_8J.demo2()))
   }

   test("Scala covariance") {
      assert(printout(Section_8.demo()) == "A\nB\n")
   }

   test("Scala non-variance") {
      import ArrayNonVariant.*
      assertTypeError("printTitles(books)")
   }

   test("function subtype") {
      def g(pub: Publication): Magazine = Magazine(pub.title, 1, pub.pageCount)

      assertCompiles("higherOrder(g)")
   }

   test("Ref covariant") {
      val ref: Ref[Book] = Ref(book1)
      assertCompiles("ref: Ref[Publication]")
   }

   test("TrashCan contravariant") {
      val can: TrashCan[Publication] = TrashCan(Logger.getAnonymousLogger)
      assertCompiles("can: TrashCan[Book]")
   }

   test("contravariant position") {
      assertDoesNotCompile("""class Ref[+A](private var contents: A):
                             |   private var count = 0
                             |
                             |   def set(value: A): Unit =
                             |      contents = value
                             |      count = 0
                             |""".stripMargin)
      assertCompiles("""class Ref[A](private var contents: A):
                       |   private var count = 0
                       |
                       |   def set(value: A): Unit =
                       |      contents = value
                       |      count = 0
                       |""".stripMargin)
   }
