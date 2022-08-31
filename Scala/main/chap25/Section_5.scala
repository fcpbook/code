package chap25

import scala.concurrent.ExecutionContext

object Section_5:
   object Scala:
      import scala.concurrent.{ Future, Promise }
      import scala.util.Try

      def apply[A](code: => A)(using exec: ExecutionContext): Future[A] =
         val promise = Promise[A]()
         exec.execute(() => promise.complete(Try(code)))
         promise.future
   end Scala

   object JavaNew:
      import java.util.concurrent.{ CompletableFuture, Future }

      def apply[A](code: => A)(using exec: ExecutionContext): Future[A] =
         val promise = CompletableFuture[A]()
         exec.execute { () =>
            try promise.complete(code)
            catch case ex: Exception => promise.completeExceptionally(ex)
         }
         promise
   end JavaNew

   object JavaOld:
      import java.util.concurrent.{ Future, FutureTask }

      def apply[A](code: => A)(using exec: ExecutionContext): Future[A] =
         val promise = FutureTask(() => code)
         exec.execute(promise)
         promise
   end JavaOld
