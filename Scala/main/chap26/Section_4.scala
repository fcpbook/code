package chap26

import tinyscalautils.threads.Executors.global

import scala.concurrent.{ ExecutionContext, Future }

object Section_4:
   object Variant1:
      def multiply(futureString: Future[String], futureCount: Future[Int]): Future[Future[String]] =
         // DON'T DO THIS!
         futureString.map(str => futureCount.map(count => str * count))

   def multiply(futureString: Future[String], futureCount: Future[Int]): Future[String] =
      futureString.flatMap(str => futureCount.map(count => str * count))

   object Variant2:
      def multiply(futureString: Future[String], futureCount: Future[Int]): Future[String] =
         futureString.zip(futureCount).map((str, count) => str * count)

   object Variant3:
      def multiply(futureString: Future[String], futureCount: Future[Int]): Future[String] =
         futureString.zipWith(futureCount)((str, count) => str * count)

   object Variant4:
      def multiply(futureString: Future[String], futureCount: Future[Int]): Future[String] =
         for str <- futureString; count <- futureCount yield str * count

   def quickSort(list: List[Int])(using ExecutionContext): Future[List[Int]] = list match
      case Nil => Future.successful(list)
      case pivot :: others =>
         val (low, high) = others.partition(_ < pivot)
         val lowFuture   = Future.delegate(quickSort(low))
         val highFuture  = quickSort(high)
         lowFuture.flatMap(lowSorted =>
            highFuture.map(highSorted => lowSorted ::: pivot :: highSorted)
         )
