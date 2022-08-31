package chap16

import tinyscalautils.text.{ PrintingMode, println }

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }

object Section_1:
   def demo1()(using ExecutionContext, PrintingMode): Unit =
      println('A')
      Future(println('B'))
      println('C')

   def demo2()(using ExecutionContext, PrintingMode): Unit =
      println('A')
      Await.ready(Future(println('B')), Duration.Inf)
      println('C')

   def demo3()(using ExecutionContext, PrintingMode): Unit =
      def slowPrint(x: Any) =
         var n = BigInt("1000000000")
         while n > 0 do n -= 1
         println(x)

      slowPrint('A')
      Future(slowPrint('B'))
      slowPrint('C')
   end demo3
