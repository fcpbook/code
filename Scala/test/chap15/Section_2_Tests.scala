package chap15

import chap15.Section_2.*
import mocks.Chap15.book1
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("flip") {
      assert(flip(0) == 1)
      assert(flip(1) == 0)
      assertTypeError("flip(42)")
   }

   test("doSomethingWithBook") {
      assert(doSomethingWithBook(book1) eq book1)
   }

   test("buffer variant") {
      import Variant.*
      val buffer = Buffer[String]()
      assert(demo(buffer) ne buffer)
   }

   test("buffer") {
      val buffer = Buffer[String]()
      assert(demo(buffer) eq buffer)
   }
