package chap24

import java.util.concurrent.{ Executor, Semaphore }

//noinspection DuplicatedCode
object Section_9:
   class Runner[A](exec: Executor)(comp: A => Unit):
      private var active = 0
      private val start  = Semaphore(1)
      private val finish = Semaphore(0)

      def run(inputs: Seq[A]): Unit =
         if inputs.nonEmpty then
            start.acquire()
            synchronized {
               active = inputs.length
               for input <- inputs do exec.execute(task(input))
            }
            finish.acquire()
            start.release()

      def addInput(input: A): Boolean = synchronized {
         if active == 0 then false
         else
            active += 1
            exec.execute(task(input))
            true
      }

      private def task(in: A): Runnable = () =>
         try comp(in)
         finally synchronized {
               active -= 1
               if active == 0 then finish.release()
            }
   end Runner
