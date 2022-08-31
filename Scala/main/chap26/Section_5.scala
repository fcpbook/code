package chap26

import mocks.Chap21.port
import mocks.Chap25.*
import mocks.Chap26.{ someConnection as connection, someRequest as request }
import tinyscalautils.threads.Executors.global

import java.net.ServerSocket
import java.util.concurrent.Executors
import scala.concurrent.duration.MILLISECONDS
import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService, Future, Promise }

//noinspection DuplicatedCode,TypeAnnotation
object Section_5:
   def demo1(): Unit =
      val futureAd: Future[Ad]     = Future(fetchAd(request))
      val data: Data               = dbLookup(request)
      val futurePage: Future[Page] = futureAd.map(ad => makePage(data, ad))
      futurePage.foreach(page => connection.write(page))

   def demo2(): Unit =
      val futureAd: Future[Ad]     = Future(fetchAd(request))
      val futureData: Future[Data] = Future(dbLookup(request))
      val futurePage: Future[Page] =
         futureData.flatMap(data => futureAd.map(ad => makePage(data, ad)))
      futurePage.foreach(page => connection.write(page))

   class Server:
      given exec: ExecutionContextExecutorService =
         ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(16))

      val server = ServerSocket(port)

      def handleConnection(connection: Connection): Unit =
         val requestF = Future(connection.read())
         val adF      = requestF.map(request => fetchAd(request))
         val dataF    = requestF.map(request => dbLookup(request))
         val pageF    = dataF.flatMap(data => adF.map(ad => makePage(data, ad)))
         dataF.foreach(data => addToLog(data))
         pageF.foreach(page => updateStats(page))
         pageF.foreach(page => {
            connection.write(page); connection.close()
         })

      def demo(): Unit = while true do handleConnection(Connection(server.accept()))
   end Server

   // noinspection TypeAnnotation,DuplicatedCode
   class ServerTimeout:
      given exec: ExecutionContextExecutorService =
         ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(16))

      val timer = Executors.newScheduledThreadPool(1)

      val server = ServerSocket(port)

      def handleConnection(connection: Connection): Unit =
         val requestF = Future(connection.read())
         val adF      = requestF.map(request => fetchAd(request))
         val dataF    = requestF.map(request => dbLookup(request))

         val pageF = dataF.flatMap { data =>
            val safeAdF =
               if adF.isCompleted then adF
               else
                  val promise = Promise[Ad]()
                  val timerF =
                     timer.schedule(() => promise.trySuccess(timeoutAd), 500, MILLISECONDS)
                  adF.foreach { ad =>
                     timerF.cancel(false)
                     promise.trySuccess(ad)
                  }
                  promise.future
            safeAdF.map(ad => makePage(data, ad))
         }

         dataF.foreach(data => addToLog(data))
         pageF.foreach(page => updateStats(page))
         pageF.foreach(page => {
            connection.write(page); connection.close()
         })
         pageF.failed.foreach { ex =>
            connection.write(errorPage(ex))
            connection.close()
         }
      end handleConnection

      def demo(): Unit = while true do handleConnection(Connection(server.accept()))
   end ServerTimeout
