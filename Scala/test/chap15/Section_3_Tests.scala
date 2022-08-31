package chap15

import chap15.Section_3.*
import org.scalatest.funsuite.AnyFunSuite

import scala.language.adhocExtensions

class Section_3_Tests extends AnyFunSuite:
   test("services and types") {
      val b1 = new Variant1.Book("A", "a", 1) with Book
      assert(b1.title == "A")
      assert(b1.author == "a")
      assert(b1.pageCount == 1)
      val b2 = new Variant2.Book(Seq("A", "a", "X", "Y")) with Book
      assert(b2.title == "A")
      assert(b2.author == "a")
      assert(b2.pageCount == 4)
   }
