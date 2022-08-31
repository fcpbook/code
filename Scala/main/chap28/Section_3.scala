package chap28

import chap28.Section_1.Strategy

import java.util.concurrent.{ Callable, ExecutorService }
import scala.concurrent.duration.NANOSECONDS
import scala.concurrent.{ ExecutionException, TimeoutException }
import scala.jdk.CollectionConverters.SeqHasAsJava

object Section_3:
   // noinspection DuplicatedCode
   class Runner[A, B](strategies: Seq[Strategy[A, B]], exec: ExecutorService):
      def compute(input: A, timeout: Double): Option[B] =
         val deadline = System.nanoTime() + (timeout * 1E9).round

         val tasks = strategies.map(strategy => strategy(input))
         val calls = tasks.map(task => (() => task.run().get): Callable[B])

         try Some(exec.invokeAny(calls.asJava, deadline - System.nanoTime(), NANOSECONDS))
         catch case _: ExecutionException | _: TimeoutException => None
         finally for task <- tasks do task.cancel()
      end compute
   end Runner
