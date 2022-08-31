package chap28

import chap28.Section_1.Strategy

import java.util.concurrent.{ Executor, ExecutorCompletionService }
import scala.annotation.tailrec
import scala.concurrent.duration.NANOSECONDS

object Section_4:
   // noinspection DuplicatedCode
   class Runner[A, B](strategies: Seq[Strategy[A, B]], exec: Executor):
      def compute(input: A, timeout: Double): Option[B] =
         val deadline = System.nanoTime() + (timeout * 1E9).round

         val tasks = strategies.map(strategy => strategy(input))
         val queue = ExecutorCompletionService[Option[B]](exec)
         for task <- tasks do queue.submit(() => task.run())

         @tailrec
         def loopQueue(pending: Int): Option[B] =
            if pending == 0 then None
            else
               queue.poll(deadline - System.nanoTime(), NANOSECONDS) match
                  case null                            => None
                  case future if future.get().nonEmpty => future.get()
                  case _                               => loopQueue(pending - 1)
                  // could be tailrec in a future Scala:
                  // case future => future.get().orElse(loopQueue(pending - 1))

         try loopQueue(tasks.length)
         finally for task <- tasks do task.cancel()
      end compute
   end Runner
