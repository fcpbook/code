package chap15

import chap15.Section_1.*
import chap15.Section_6.Book
import mocks.Chap15.{ book1, book2, book3 as book }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_1_Tests extends AnyFunSuite:
   Book("A","B",0) // to keep imports
   
   test("errors") {
      assertTypeError {
         """val books: List[Book] = List(book1, book2, 42)
           |for book <- books do println(book.title)
           |""".stripMargin
      }
      assertTypeError {
         """val books = List(book1, book2)
           |for book <- books do println(book.title, 42)
           |""".stripMargin
      }
      assertTypeError {
         """var bookOrString = book
           |bookOrString = "Le Comte de Monte-Cristo"
           |""".stripMargin
      }
      assertCompiles {
         """var bookOrString: Book | String = book
           |bookOrString = "Le Comte de Monte-Cristo"
           |""".stripMargin
      }
   }

   test("set add") {
      import Section_1J.Demo
      Demo.added = 0
      Demo.demo(book)
      assert(Demo.added == 1)
      Demo.demo(book1)
      assert(Demo.added == 1)
      Demo.demo(book2)
      assert(Demo.added == 2)
   }

   test("demos") {
      assert(demo1 == "A: 12.95")
      assert(demo2 == "12.95: A")
   }

   test("printTitles") {
      assert(printout(printTitle(book)) == "C\n")
      assertThrows[IllegalArgumentException](printTitle(None))
   }
