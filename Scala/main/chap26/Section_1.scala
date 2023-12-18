package chap26

import mocks.Chap25.*
import mocks.Chap26.{ someConnection as connection, someRequest as request }
import tinyscalautils.threads.Executors.global as exec

import java.util.concurrent.{ ExecutorService, Executors }

object Section_1:
   // DON'T DO THIS!
   def quickSort(list: List[Int], exec: ExecutorService): List[Int] =
      list match
         case Nil => list
         case pivot :: others =>
            val (low, high) = others.partition(_ < pivot)
            val lowFuture   = exec.submit(() => quickSort(low, exec))
            val highSorted  = quickSort(high, exec)
            lowFuture.get() ::: pivot :: highSorted

   def demo1(): List[Int] =
      val exec = Executors.newFixedThreadPool(3)
      try quickSort(List(1, 6, 8, 6, 1, 8, 2, 8, 9), exec) // List(1, 1, 2, 6, 6, 8, 8, 8, 9)
      finally exec.shutdown()

   def demo2(): Unit =
      import java.util.concurrent.Future

      val futureAd: Future[Ad] = exec.submit(() => fetchAd(request))
      val data: Data           = dbLookup(request)
      val page: Page           = makePage(data, futureAd.get())
      connection.write(page)
