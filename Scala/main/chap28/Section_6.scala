package chap28

import chap28.Section_1.Strategy

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{ CompletableFuture, Executor }
import scala.concurrent.duration.NANOSECONDS

object Section_6:
   // FORMAT: OFF
   def findFirst[A](futures: Seq[CompletableFuture[A]],
                    test: A => Boolean): CompletableFuture[Option[A]] =
      if futures.isEmpty then CompletableFuture.completedFuture(None)
      else
         val promise = CompletableFuture[Option[A]]()
         val active  = AtomicInteger(futures.length)

         for future <- futures do
            future.handle { (value, ex) =>
               if !promise.isDone then
                  if (ex eq null) && test(value) then promise.complete(Some(value))
                  else if active.decrementAndGet() == 0 then promise.complete(None)
            }
         promise
   end findFirst
   // FORMAT: ON

   // noinspection DuplicatedCode
   class Runner[A, B](strategies: Seq[Strategy[A, B]], exec: Executor):
      def compute(input: A, timeout: Double): CompletableFuture[Option[B]] =
         val deadline = System.nanoTime() + (timeout * 1E9).round

         val tasks   = strategies.map(strategy => strategy(input))
         val futures = tasks.map(task => CompletableFuture.supplyAsync(() => task.run(), exec))

         val first = findFirst(futures, _.nonEmpty)
            .thenApply(_.flatten)
            .completeOnTimeout(None, deadline - System.nanoTime(), NANOSECONDS)

         first.handle((_, _) => for task <- tasks do task.cancel())
         first
      end compute
   end Runner
