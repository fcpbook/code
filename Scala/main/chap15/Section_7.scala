package chap15

import chap15.Section_6.{ Book, Magazine, Publication }
import mocks.Chap15.{ magazine, book1 as book }

// noinspection TypeAnnotation
object Section_7:
   def displayBook(book: Book)        = println(s"${book.title} by ${book.author}")
   def displayMagazine(mag: Magazine) = println(s"${mag.title} (${mag.pageCount} pages)")

   def display(book: Book)    = println(s"${book.title} by ${book.author}")
   def display(mag: Magazine) = println(s"${mag.title} (${mag.pageCount} pages)")

   def withHash[T](value: T): (T, Int) = (value, value.##)

   def bookWithHash(book: Book): (Book, Int)                 = (book, book.##)
   def magazineWithHash(magazine: Magazine): (Magazine, Int) = (magazine, magazine.##)

   val hashedBook: (Book, Int)         = withHash(book)
   val hashedMagazine: (Magazine, Int) = withHash(magazine)

   def printTitles(pubs: List[Publication]) = for pub <- pubs do println(pub.title)

   // rejected by the compiler
   // def displayCollection(pubs: List[Publication]) = for pub <- pubs do display(pub)

   object Bad:
      // DON'T DO THIS!
      def displayCollection(pubs: List[Publication]) =
         for pub <- pubs do
            pub match
               case book: Book         => display(book)
               case magazine: Magazine => display(magazine)

   object Good:
      trait Publication:
         def title: String
         def pageCount: Int
         def display(): Unit

      case class Book(title: String, author: String, pageCount: Int) extends Publication:
         def display() = println(s"$title by $author")

      case class Magazine(title: String, number: Int, pageCount: Int) extends Publication:
         def display() = println(s"$title ($pageCount pages)")

      def displayCollection(pubs: List[Publication]) = for pub <- pubs do pub.display()

      case class Report(title: String, pageCount: Int) extends Publication:
         def display() = println(s"Report: $title")

      def demo(book: Book, magazine: Magazine, report: Report): Unit =
         displayCollection(List(book, magazine, report))
   end Good
