package chap21

import chap12.Section_3.times
import chap19.Section_5.SafeStringList
import mocks.Chap21.N
import tinyscalautils.text.{ PrintingMode, println }

import java.util.concurrent.{ ExecutorService, Executors }
import scala.concurrent.duration.MINUTES

//noinspection DuplicatedCode
object Section_1:
   def demo1()(using PrintingMode): ExecutorService =
      println("START")

      class LetterPrinter(letter: Char) extends Runnable:
         def run(): Unit = println(letter)

      val exec = Executors.newFixedThreadPool(4)

      exec.execute(LetterPrinter('A'))
      exec.execute(LetterPrinter('B'))
      exec.execute(LetterPrinter('C'))

      println("END")
      exec // returned so it can be shutdown
   end demo1

   def demo2()(using PrintingMode): Unit =
      println("START")

      class LetterPrinter(letter: Char) extends Runnable:
         def run(): Unit = println(letter)

      val exec = Executors.newFixedThreadPool(4)

      exec.execute(LetterPrinter('A'))
      exec.execute(LetterPrinter('B'))
      exec.execute(LetterPrinter('C'))

      exec.shutdown()
      exec.awaitTermination(5, MINUTES)

      println("END")
   end demo2

   def demo3(assert: Boolean => Unit): Unit =
      val exec   = Executors.newCachedThreadPool()
      val shared = SafeStringList()

      for i <- 1 to N do exec.execute(() => 5 times shared.add(i.toString))

      exec.shutdown()
      exec.awaitTermination(5, MINUTES)

      assert(shared.size == 5 * N)
      assert((1 to N).forall(i => shared.getAll.count(_ == i.toString) == 5))
   end demo3
