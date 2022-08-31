package chap24

import mocks.Chap24.sequentialRunner
import tinyscalautils.text.PrintingMode

object Section_1:
   class Runner[A](comp: A => Unit):
      def run(inputs: Seq[A]): Unit = for input <- inputs do comp(input)

   def demo()(using PrintingMode): Unit = Demos[Runner]().demo()
