package chap15

object Section_6:
   trait Publication:
      def title: String
      def pageCount: Int

   case class Book(title: String, author: String, pageCount: Int)  extends Publication
   case class Magazine(title: String, number: Int, pageCount: Int) extends Publication

   def printTitle(publication: Publication): Unit = println(publication.title)

   // rejected by the compiler
   // case class Book(author: String, pageCount: Int) extends Publication

   object Variant1:
      case class Book(author: String, pageCount: Int) // OK
      val book: Book = mocks.Chap15.noTitleBook
      // printTitle(book) // rejected by the compiler

   object Variant2:
      case class Book(title: String, author: String, pageCount: Int)
      val book: Book = mocks.Chap15.notPubBook
      // printTitle(book) // rejected by the compiler
