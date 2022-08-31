package chap15

import chap15.Section_6.Book
import mocks.Chap15.{ price, book1 as book }

import scala.annotation.nowarn

//noinspection TypeCheckCanBeMatch
object Section_1:
   def demo1: String = book.title + ": " + price
   def demo2: String = price + ": " + book.title: @nowarn

   // DON'T DO THIS!
   def printTitle(item: Any): Unit =
      if item.isInstanceOf[Book] then
         val book = item.asInstanceOf[Book]
         println(book.title)
      else throw IllegalArgumentException("not a book")
