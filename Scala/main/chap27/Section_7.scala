package chap27

import scala.concurrent.{ ExecutionContext, Future }

object Section_7:
   class Demo(using ExecutionContext):
      println("START")

      lazy val future1: Future[Int] = Future.delegate {
         println("future 1 starts")
         val future2: Future[String] = Future.delegate {
            println("future 2 starts")
            future1.map(n => n.toString)
         }
         future2.map(str => str.length)
      }

      println("END")
   end Demo
