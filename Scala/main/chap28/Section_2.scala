package chap28

import chap28.Section_1.Strategy

import java.util.concurrent.{ Executors, Future }
import scala.concurrent.TimeoutException
import scala.concurrent.duration.NANOSECONDS

object Section_2:
   class Runner[A, B](strategies: Seq[Strategy[A, B]]):
      def compute(input: A, timeout: Double): Option[B] =
         val exec  = Executors.newSingleThreadExecutor()
         val tasks = strategies.map(strategy => strategy(input))

         val future: Future[Option[B]] = exec.submit { () =>
            tasks.view.flatMap(task => task.run()).headOption
         }
         exec.shutdown()

         try future.get((timeout * 1E9).round, NANOSECONDS)
         catch
            case _: TimeoutException =>
               for task <- tasks do task.cancel()
               None
      end compute
   end Runner
