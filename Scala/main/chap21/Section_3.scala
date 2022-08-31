package chap21

import mocks.Chap21.logger
import tinyscalautils.text.{ PrintingMode, println }

import java.util.concurrent.{
   ArrayBlockingQueue,
   Executors,
   ScheduledExecutorService,
   ThreadPoolExecutor
}
import scala.concurrent.duration.{ HOURS, MINUTES, SECONDS }

//noinspection ScalaUnusedSymbol
object Section_3:
   // FORMAT: OFF
   object exec extends ThreadPoolExecutor(
      4,
      16,
      3, MINUTES,
      ArrayBlockingQueue(128),
      ThreadPoolExecutor.CallerRunsPolicy()
   ):
      override def beforeExecute(thread: Thread, task: Runnable): Unit =
         logger.info(() => s"${thread.getName} starts task $task")

   def demo()(using PrintingMode): ScheduledExecutorService =
      println("START")

      val exec = Executors.newScheduledThreadPool(2)

      exec.schedule((() => println('A')): Runnable, 5, SECONDS)
      exec.scheduleAtFixedRate(() => println('B'), 3, 10, SECONDS)
      exec.scheduleWithFixedDelay(() => println('C'), 3, 10, SECONDS)

      println("END")
      exec // returned so it can be shutdown
   end demo

   @main def TestTimer(): Unit = // runs for 1 hour...
      import tinyscalautils.text.threadTimeDemoMode
      val exec = demo()
      exec.schedule((() => exec.shutdown()): Runnable, 1, HOURS)
