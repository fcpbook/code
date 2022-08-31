package chap26

import mocks.Chap25.*
import mocks.Chap26.{ someConnection as connection, someRequest as request }
import tinyscalautils.text.{ PrintingMode, println }
import tinyscalautils.threads.Executors.global

import scala.concurrent.{ ExecutionContext, Future }

object Section_2:
   def demo1(exec: ExecutionContext)(using PrintingMode): Unit =
      println("START")
      given ExecutionContext = exec // a thread pool

      val future1: Future[Int] = mocks.Chap26.successfulFuture // a future that succeeds with 42 after 1 second
      val future2: Future[String] = mocks.Chap26.failedFuture // a future that fails with NPE after 2 seconds
      future1.onComplete(println)
      future2.onComplete(println)

      println("END")
   end demo1

   def demo2(): Unit =
      val futureAd: Future[Ad] = Future(fetchAd(request))
      val data: Data           = dbLookup(request)
      futureAd.onComplete { ad =>
         val page = makePage(data, ad.get)
         connection.write(page)
      }
