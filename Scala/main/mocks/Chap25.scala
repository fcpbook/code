package mocks

import tinyscalautils.timing.delay

import java.net.Socket
import java.util.concurrent.{ BlockingQueue, LinkedBlockingQueue }
import scala.annotation.unused
import scala.io.Source
import scala.util.Using

object Chap25:
   val serverOutput: BlockingQueue[String] = LinkedBlockingQueue()

   def serverOut(id: Int, msg: String): Unit =
      serverOutput.put(if id > 0 then delay(id.sign)(s"$msg($id)") else s"$msg(${-id})")

   def dbLookup(request: Request): Data =
      serverOut(request.id, "D")
      Data(request.id)

   def addToLog(data: Data): Unit = serverOut(data.id, "L")

   def fetchAd(request: Request): Ad =
      if request.id < 0 then throw AdFetchingException()
      else if request.id == Int.MaxValue then
         serverOut(0, "T")
         delay(2.0)(throw AdFetchingException())
      else
         serverOut(request.id, "F")
         someAd

   def makePage(data: Data, @unused ad: Ad): Page =
      serverOut(data.id, "P")
      Page(data.id)

   def updateStats(page: Page): Unit = serverOut(page.id, "S")

   class Connection(id: Int):
      def this(socket: Socket) = this {
         Using.resource(Source.fromInputStream(socket.getInputStream))(in => in.mkString.trim.toInt)
      }

      def read(): Request =
         serverOut(id, "R")
         Request(id)

      def write(@unused page: Page): Unit = serverOut(id, "W")

      def close(): Unit = serverOut(id, "C")
   end Connection

   class Data(val id: Int)

   class Page(val id: Int):
      def length: Int = id

   class Request(val id: Int)

   class Ad

   class AdFetchingException extends Exception

   val someAd: Ad    = Ad()
   val failedAd: Ad  = Ad()
   val timeoutAd: Ad = Ad()

   def errorPage(@unused ex: Throwable): Page = Page(-1)
