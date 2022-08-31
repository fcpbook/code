package mocks

import chap15.BookPublisher
import chap15.Section_6.{ Book, Magazine, Publication }
import tinyscalautils.util.{ FastRandom, nextInt }

import java.util

//noinspection TypeAnnotation
object Chap15:
   val book1    = chap15.Section_6.Book("A", "a", 1)
   val book2    = chap15.Section_6.Book("B", "b", 2)
   val book3    = chap15.Section_6.Book("C", "c", 3)
   val magazine = chap15.Section_6.Magazine("M", 1, 2)
   val books    = util.HashSet(util.Set.of(book1))

   def price                    = 12.95f
   def x: Int                   = FastRandom.nextInt(0 to 1)
   def solutionIsFound: Boolean = FastRandom.nextBoolean()
   def value                    = 1

   val noTitleBook = chap15.Section_6.Variant1.Book("A", 1)
   val notPubBook  = chap15.Section_6.Variant2.Book("A", "a", 1)

   def higherOrder(f: Book => Publication): Unit = f(book1)

   case class OrderedBook(title: String, pageCount: Int) extends Publication, Ordered[OrderedBook]:
      override def compare(that: OrderedBook): Int = this.pageCount.compareTo(that.pageCount)

   val orderedBook1 = OrderedBook("A", 2)
   val orderedBook2 = OrderedBook("B", 1)

   case class ComparableBook(title: String, pageCount: Int)
       extends Publication,
         Comparable[ComparableBook]:
      override def compareTo(that: ComparableBook): Int = this.pageCount.compareTo(that.pageCount)

   val comparableBook1 = ComparableBook("A", 100)
   val comparableBook2 = ComparableBook("B", 10)

   def service1: Int              = 10
   def service2(str: String): Int = str.length
   def service3(n: Int): String   = s"[$n]"
   def service4: Double           = math.Pi

   val publishers: List[BookPublisher]       = List(BookPublisher())
   val publishers1: util.List[BookPublisher] = util.List.of(BookPublisher())
   val publishers2: util.List[() => Book]    = util.List.of(BookPublisher())

   case class Noble(name: String, title: String)
