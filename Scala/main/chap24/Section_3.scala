package chap24

import mocks.Chap24.boundedRunner
import tinyscalautils.text.PrintingMode

import java.util.concurrent.ConcurrentLinkedQueue
import scala.jdk.CollectionConverters.SeqHasAsJava

object Section_3:
   class Runner[A](bound: Int)(comp: A => Unit):
      def run(inputs: Seq[A]): Unit =
         val queue = ConcurrentLinkedQueue(inputs.asJava)

         val task: Runnable = () =>
            var input = Option(queue.poll())
            while input.nonEmpty do
               comp(input.get)
               input = Option(queue.poll())

         val threads = Seq.fill(bound min inputs.length)(Thread(task))
         for thread <- threads do thread.start()
         for thread <- threads do thread.join()
      end run
   end Runner

   def demo()(using PrintingMode): Unit = Demos[Runner]().demoBound()
