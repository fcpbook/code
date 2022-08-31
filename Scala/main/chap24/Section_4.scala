package chap24

import mocks.Chap24.localPoolRunner
import tinyscalautils.text.PrintingMode

import java.util.concurrent.Executors
import scala.concurrent.duration.NANOSECONDS

object Section_4:
   class Runner[A](bound: Int)(comp: A => Unit):
      def run(inputs: Seq[A]): Unit =
         val exec = Executors.newFixedThreadPool(bound)

         for input <- inputs do exec.execute(() => comp(input))

         exec.shutdown()
         exec.awaitTermination(Long.MaxValue, NANOSECONDS)
      end run
   end Runner

   def demo()(using PrintingMode): Unit = Demos[Runner]().demoBound()
