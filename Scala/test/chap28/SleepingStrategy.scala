package chap28

import chap28.Section_1.Strategy
import tinyscalautils.timing.sleep

import java.util.concurrent.FutureTask
import scala.util.Try

class SleepingStrategy(val name: String, timing: (Char, Int)*) extends Strategy[Char, Int]:
   private val time = Map.from(timing)

   def apply(input: Char): Task = SleepingTask(input)

   private class SleepingTask(c: Char) extends Task:
      private val ft = FutureTask { () =>
         time.get(c).flatMap { seconds =>
            sleep(seconds.abs)
            Option.unless(seconds < 0)(c.toInt)
         }
      }

      def run(): Option[Int] =
         ft.run()
         Try(ft.get).toOption.flatten

      def cancel(): Unit = ft.cancel(true)
   end SleepingTask
end SleepingStrategy

val S1 = SleepingStrategy("S1", 'A' -> -1)
val S2 = SleepingStrategy("S2", 'A' -> 3)
val S3 = SleepingStrategy("S3", 'A' -> 2, 'B' -> -2)
