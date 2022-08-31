package chap24

import mocks.Chap24.poolRunner
import tinyscalautils.text.PrintingMode

import java.util.concurrent.{ CountDownLatch, Executor }

object Section_5:
   class Runner[A](exec: Executor)(comp: A => Unit):
      def run(inputs: Seq[A]): Unit =
         val done = CountDownLatch(inputs.length)

         for input <- inputs do
            val task: Runnable = () =>
               try comp(input)
               finally done.countDown()
            exec.execute(task)

         done.await()
      end run
   end Runner

   def demo()(using PrintingMode): Unit = Demos[Runner]().demoPool()
