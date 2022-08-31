package chap28

import chap28.Section_1.Strategy
import chap28.Section_5.{ completeOnTimeout, findFirst }

import java.util.concurrent.ScheduledExecutorService
import scala.annotation.tailrec
import scala.collection.concurrent.TrieMap
import scala.concurrent.duration.NANOSECONDS
import scala.concurrent.{ ExecutionContext, Future, Promise }

object Section_7:
   // FORMAT: OFF
   class Runner[A, B](strategies: Seq[Strategy[A, B]], exec: ExecutionContext)
                     (using ScheduledExecutorService)(using ExecutionContext):
      private val cache = TrieMap.empty[A, (Double, Future[Option[B]])]

      def compute(input: A, timeout: Double): Future[Option[B]] =
         val deadline = System.nanoTime() + (timeout * 1E9).round

         def hasResult(future: Future[Option[B]]): Boolean =
            future.value.flatMap(_.toOption).flatten.nonEmpty

         @tailrec
         def searchCache(): Future[Option[B]] = cache.get(input) match
            case None => // case 1
               val promise = Promise[Option[B]]()
               if cache.putIfAbsent(input, (timeout, promise.future)).isEmpty
               then doCompute(None, promise)
               else searchCache()

            case Some((_, future)) if hasResult(future) => // case 2
               future

            case Some(cached @ (time, future)) => // case 3
               if time >= timeout then // case 3(a)
                  future.completeOnTimeout(deadline - System.nanoTime(), NANOSECONDS)(None)
               else // case 3(b)
                  val promise = Promise[Option[B]]()
                  if cache.replace(input, cached, (timeout, promise.future))
                  then doCompute(Some(future), promise)
                  else searchCache()
         end searchCache

         def doCompute(future: Option[Future[Option[B]]],
                       promise: Promise[Option[B]]): Future[Option[B]] =
            val tasks   = strategies.map(strategy => strategy(input))
            val futures = tasks.map(task => Future(task.run())(using exec)) ++ future

            findFirst(futures, _.nonEmpty)
               .map(_.flatten)
               .completeOnTimeout(deadline - System.nanoTime(), NANOSECONDS)(None)
               .onComplete { result =>
                  promise.complete(result)
                  for task <- tasks do task.cancel()
               }
            promise.future
         end doCompute

         searchCache()
      end compute
   end Runner
