package chap15

import chap15.Section_6.Book
import mocks.Chap15.*

//noinspection TypeAnnotation,ScalaUnusedSymbol
object Section_5:
   var demo1 = if x > 0 then List(1, 2, 3) else List.empty      // List[Int]
   var demo2 = if x > 0 then List(1, 2, 3) else Vector(1, 2, 3) // Seq[Int]
   var demo3 = if x > 0 then List(1, 2, 3) else "123"           // AnyRef

   var demo4 = List(Some(42), Some(31)) // List[Some[Int]]
   var demo5 = List(Some(42), None)     // List[Option[Int]]

   var demo6 = 0

   var demo7 = List(book1, book2)         // List[Book]
   var demo8 = List(book1, book2, "book") // List[AnyRef]

   // (Int => Int | Boolean) & Equals & scala.collection.immutable.Iterable[Int] =
   var demo9 = if x > 0 then List(1, 2, 3) else Set(4)

   def demo10(v: Iterable[Int] = demo9): Int =
      v.iterator.next() // 1 or 4, of type Int

   def demo11(v: Int => Int | Boolean = demo9): Int | Boolean =
      v(1) // 2 or false, of type Int | Boolean

   def demo12(v: Equals = demo9): Boolean =
      v == List(1, 2, 3) || v == Set(4) // true

   object Demo13:
      var books: List[Book] = List.empty
      books ::= book1

   object Demo14:
      var books = List.empty: List[Book]
      books ::= book1

   object Demo15:
      var books = List.empty[Book]
      books ::= book1

   object Demo16:
      var solution: Option[Int] = None
      if solutionIsFound then solution = Some(value)

   type ResultTask = Runnable { def result: Int }

   val task: ResultTask = new Runnable() {
      var result = -1

      def run(): Unit = result = 42
   }

   val chars: Seq[Char] = "a string"
