package chap25

import mocks.Chap21.port
import mocks.Chap25.*
import tinyscalautils.threads.Executors.global as someExecutor

import java.net.ServerSocket
import java.util.concurrent.{ ExecutorService, Executors, Future }

//noinspection DuplicatedCode,TypeAnnotation
object Section_2:
   def demo(assert: Boolean => Unit): Unit =
      val exec: ExecutorService = someExecutor

      def makeStrings(n: Int, str: String): List[String] = List.fill(n)(str)

      val f1: Future[List[String]] = exec.submit(() => makeStrings(5, "T1"))
      val f2: Future[List[String]] = exec.submit(() => makeStrings(5, "T2"))

      val strings: List[String] = f1.get() ::: f2.get()
      assert(strings.size == 10)
   end demo

   class ServerSeq:
      val server = ServerSocket(port)
      val exec   = Executors.newFixedThreadPool(16)

      def handleConnection(connection: Connection): Unit =
         val request = connection.read() // get request from client
         val data    = dbLookup(request) // search the database
         addToLog(data) // log the results of the search
         val ad   = fetchAd(request)   // fetch an ad
         val page = makePage(data, ad) // create a page
         connection.write(page) // reply to the client
         connection.close()     // close the socket
         updateStats(page)      // record some statistics

      def demo(): Unit =
         while true do
            val socket = server.accept()
            exec.execute(() => handleConnection(Connection(socket)))
   end ServerSeq

   class ServerPar:
      val server = ServerSocket(port)
      val exec1  = Executors.newFixedThreadPool(12)
      val exec2  = Executors.newFixedThreadPool(8)

      def handleConnection(connection: Connection, exec: ExecutorService): Unit =
         val request  = connection.read()
         val futureAd = exec.submit(() => fetchAd(request))
         val data     = dbLookup(request)
         addToLog(data)
         val page = makePage(data, futureAd.get())
         connection.write(page)
         connection.close()
         updateStats(page)

      def demo(): Unit =
         while true do
            val socket = server.accept()
            exec1.execute(() => handleConnection(Connection(socket), exec2))
   end ServerPar
