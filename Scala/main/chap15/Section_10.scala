package chap15

import chap15.Section_6.{ Book, Magazine, Publication }
import mocks.Chap15.Noble

import java.util.logging.Logger
import scala.annotation.unused
import scala.collection.SortedSet
import scala.language.reflectiveCalls
import scala.reflect.Selectable.reflectiveSelectable

//noinspection TypeAnnotation
//noinspection ScalaWeakerAccess
object Section_10:
   case class Report(title: String, number: Int) // does not refer to Publication

   object StructuralTypes:
      type Titled = { def title: String }

      def printTitle(doc: Titled): Unit = println(doc.title)

      def demo1(): Unit =
         val report = Report("Count to count: from Monte-Cristo to Mathias Sandorf", 123)
         printTitle(report) // prints "Count to count: from Monte-Cristo to Mathias Sandorf"

      def demo2(): Unit =
         val person = Noble(name = "Edmond Dantès", title = "Comte de Monte-Cristo")
         printTitle(person) // prints "Comte de Monte-Cristo"

   end StructuralTypes

   // define a Titled type class, with convenience methods
   trait Titled[A]:
      def titleOf(document: A): String

   object ExplicitEvidence:
      def printTitle[A](doc: A, titledEvidence: Titled[A]): Unit =
         println(titledEvidence.titleOf(doc))

      object ReportsAreTitled extends Titled[Report]:
         def titleOf(report: Report): String = report.title

      def demo(report: Report): Unit =
         printTitle(report, ReportsAreTitled) // prints the string returned by report.title
   end ExplicitEvidence

   object ImplicitEvidence:
      def printTitle[A : Titled](doc: A): Unit =
         val titledEvidence = summon[Titled[A]]
         println(titledEvidence.titleOf(doc))

      given Titled[Report] with
         def titleOf(report: Report): String = report.title

      def demo(report: Report): Unit =
         printTitle(report) // prints the string returned by report.title
   end ImplicitEvidence

   object Titled:
      def apply[A : Titled]: Titled[A] = summon[Titled[A]]

   extension [A : Titled](document: A) def title: String = Titled[A].titleOf(document)

   // use the type class in methods and classes, e.g.:
   def printTitle[A : Titled](document: A): Unit = println(document.title)

   // make books, magazines, and reports members of the type class:
   given Titled[Report]   = _.title
   given Titled[Book]     = _.title
   given Titled[Magazine] = _.title

   def demo1(report: Report): Unit     = printTitle(report)   // OK
   def demo2(book: Book): Unit         = printTitle(book)     // OK
   def demo3(magazine: Magazine): Unit = printTitle(magazine) // OK

   def printTitlesInOrder[A <: Publication : Ordering](pubs: Set[A]): Unit =
      for pub <- SortedSet.from(pubs) do println(pub.title)

   def demo4(): Unit =
      case class Memo(header: String)
      val memo = Memo("Famous Counts")

      given Titled[Memo] = _.header

      printTitle(memo) // prints "Famous Counts"

   @unused
   object PerTypeValues:
      trait Titled[A]:
         def titleOf(document: A): String
         def logger: Logger = Logger.getAnonymousLogger

      given Titled[Book] with
         def titleOf(book: Book) = book.title
         override def logger     = Logger.getLogger("book_logger")
   end PerTypeValues

   object DoubleAverage:
      def average(numbers: Seq[Double]): Double =
         if numbers.isEmpty then 0.0 else numbers.sum / numbers.length.toDouble

   object Variant:
      def average[A : Fractional](numbers: Seq[A]): A =
         if numbers.isEmpty then Fractional[A].zero
         else Fractional[A].div(numbers.sum, Fractional[A].fromInt(numbers.length))

   def average[A : Fractional](numbers: Seq[A]): A =
      val evidence = Fractional[A]
      import evidence.{ fromInt, zero }

      import math.Fractional.Implicits.infixFractionalOps

      if numbers.isEmpty then zero else numbers.sum / fromInt(numbers.length)
   end average

   val doubles: List[Double]      = List(1.2, 2.4)
   val decimals: List[BigDecimal] = List(1.2, 2.4)

   def demo5: Double     = average(doubles)                // 1.7999999999999998, as a Double
   def demo6: Double     = average(List.empty[Double])     // 0.0, as a Double
   def demo7: BigDecimal = average(decimals)               // 1.8, as a BigDecimal
   def demo8: BigDecimal = average(List.empty[BigDecimal]) // 0, as a BigDecimal
