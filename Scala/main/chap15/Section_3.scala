package chap15

//noinspection ZeroIndexToHead
object Section_3:
   object Variant1:
      case class Book(title: String, author: String, pageCount: Int)

   object Variant2:
      class Book(pages: Seq[String]):
         def title: String  = pages(0)
         def author: String = pages(1)
         def pageCount: Int = pages.length

   trait Book:
      def title: String
      def author: String
      def pageCount: Int
