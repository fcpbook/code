package chap24

import mocks.Chap24.boundedPoolRunner
import tinyscalautils.text.PrintingMode

import java.util.concurrent.{ Executor, Semaphore }

object Section_6:
   class Runner[A](exec: Executor, bound: Int)(comp: A => Unit):
      def run(inputs: Seq[A]): Unit =
         val canStart = Semaphore(bound)

         for input <- inputs do
            val task: Runnable = () =>
               try comp(input)
               finally canStart.release()

            canStart.acquire()
            exec.execute(task)
         end for

         canStart.acquire(bound)
      end run
   end Runner

   def demo()(using PrintingMode): Unit = Demos[Runner]().demoPoolBound()
