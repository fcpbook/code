package chap26

import mocks.Chap26.write
import tinyscalautils.threads.Executors.global

import scala.concurrent.{ Future, Promise }
import scala.util.{ Failure, Success, Try }

object Section_3:
   def multiplyAndWrite(futureString: Future[String], count: Int): Unit =
      futureString.onComplete {
         case Success(str) => write(str * count)
         case Failure(e)   => write(s"exception: ${e.getMessage}")
      }

   object MultiplyVariant:
      def multiply(futureString: Future[String], count: Int): Future[String] =
         val promise = Promise[String]()
         futureString.onComplete {
            case Success(str) => promise.success(str * count)
            case Failure(e)   => promise.failure(e)
         }
         promise.future

   object MapVariant:
      def map[A, B](future: Future[A], f: A => B): Future[B] =
         val promise = Promise[B]()
         future.onComplete {
            case Success(value) => promise.complete(Try(f(value)))
            case Failure(e)     => promise.failure(e)
         }
         promise.future

   def map[A, B](future: Future[A], f: A => B): Future[B] =
      val promise = Promise[B]()
      future.onComplete(tryValue => promise.complete(tryValue.map(f)))
      promise.future

   def multiply(futureString: Future[String], count: Int): Future[String] =
      futureString.map(str => str * count)
