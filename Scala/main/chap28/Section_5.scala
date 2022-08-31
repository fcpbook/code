package chap28

import chap28.Section_1.Strategy

import java.util.concurrent.ScheduledExecutorService
import scala.concurrent.duration.{ NANOSECONDS, TimeUnit }
import scala.concurrent.{ ExecutionContext, Future, Promise }

object Section_5:
   // FORMAT: OFF
   extension [A](future: Future[A])
      def completeOnTimeout(timeout: Long, unit: TimeUnit)(fallbackCode: => A)(
          using exec: ExecutionContext, timer: ScheduledExecutorService
      ): Future[A] =
         if future.isCompleted then future
         else
            val promise    = Promise[A]()
            val complete   = (() => promise.completeWith(Future(fallbackCode))): Runnable
            val completion = timer.schedule(complete, timeout, unit)

            future.onComplete { result =>
               completion.cancel(false)
               promise.tryComplete(result)
            }
            promise.future
      end completeOnTimeout
   // FORMAT: ON

   def findFirst[A](futures: Seq[Future[A]], test: A => Boolean)(
       using ExecutionContext
   ): Future[Option[A]] =
      if futures.isEmpty then Future.successful(None)
      else
         Future.firstCompletedOf(futures).flatMap { _ =>
            val (finished, running) = futures.partition(_.isCompleted)
            finished.flatMap(_.value.get.toOption).find(test) match
               case None  => findFirst(running, test)
               case found => Future.successful(found)
         }
   end findFirst

   // noinspection DuplicatedCode
   // FORMAT: OFF
   class Runner[A, B](strategies: Seq[Strategy[A, B]], exec: ExecutionContext)
                     (using ScheduledExecutorService)(using ExecutionContext):
      def compute(input: A, timeout: Double): Future[Option[B]] =
         val deadline = System.nanoTime() + (timeout * 1E9).round

         val tasks   = strategies.map(strategy => strategy(input))
         val futures = tasks.map(task => Future(task.run())(using exec))

         val first = findFirst(futures, _.nonEmpty)
            .map(_.flatten)
            .completeOnTimeout(deadline - System.nanoTime(), NANOSECONDS)(None)

         first.onComplete(_ => for task <- tasks do task.cancel())
         first
      end compute
   end Runner
