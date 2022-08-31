package chap24

import mocks.Chap24.{ newThreadRunner, newThreadRunnerBad }
import tinyscalautils.text.PrintingMode

object Section_2:
   class Runner[A](comp: A => Unit):
      def run(inputs: Seq[A]): Unit =
         val threads = inputs.map(input => Thread(() => comp(input)))
         for thread <- threads do thread.start()
         for thread <- threads do thread.join()

      def run_bad(inputs: Seq[A]): Unit =
         val threads = inputs.map(input => Thread(() => comp(input)))
         // DON'T DO THIS!
         for thread <- threads do
            thread.start()
            thread.join()
   end Runner

   def demo1()(using PrintingMode): Unit = Demos[Runner]().demo()
   def demo2()(using mode: PrintingMode): Unit = Demos[Runner](using newThreadRunnerBad, mode).demo()
