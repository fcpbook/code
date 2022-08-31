package chap24

import mocks.Chap24.parRunner
import tinyscalautils.text.PrintingMode

import scala.collection.parallel.CollectionConverters.ImmutableSeqIsParallelizable

object Section_7:
   class Runner[A](comp: A => Unit):
      def run(inputs: Seq[A]): Unit = for input <- inputs.par do comp(input) // or: inputs.par.foreach(comp)

   def demo()(using PrintingMode): Unit = Demos[Runner]().demo()
