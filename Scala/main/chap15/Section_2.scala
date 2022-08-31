package chap15

import chap15.Section_6.Book

//noinspection ScalaUnusedSymbol
object Section_2:
   def flip(x: 0 | 1): 0 | 1 = if x == 0 then 1 else 0

   def doSomethingWithBook(book: Book): book.type =
      book // do something with book and return it

   object Variant:
      class Buffer[A]:
         def append(value: A): Buffer[A] = Buffer() // a different buffer

      def demo(buffer: Buffer[String]): Buffer[String] = buffer.append("foo").append("bar")
   end Variant

   class Buffer[A]:
      def append(value: A): this.type = this // the same buffer

   def demo(buffer: Buffer[String]): buffer.type = buffer.append("foo").append("bar")
