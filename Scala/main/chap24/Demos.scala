package chap24

import mocks.Chap24.{ Runner, run, sleepTask }
import tinyscalautils.text.{ PrintingMode, println }

import java.util.concurrent.Executors

class Demos[R[_] : Runner](using PrintingMode):
   def demo(): Unit =
      println("START")

      val runner = Runner(sleepTask)
      runner.run(Seq(2, 1, 3))

      println("END")
   end demo

   def demoBound(): Unit =
      println("START")

      val runner = Runner(2)(sleepTask)
      runner.run(Seq(2, 1, 3))

      println("END")
   end demoBound

   def demoPool(): Unit =
      println("START")

      val exec   = Executors.newFixedThreadPool(2)
      val runner = Runner(exec)(sleepTask)
      runner.run(Seq(2, 1, 3))

      println("END")
      exec.shutdown()
   end demoPool

   def demoPoolBound(): Unit =
      println("START")

      val exec   = Executors.newCachedThreadPool()
      val runner = Runner(exec, 2)(sleepTask)
      runner.run(Seq(2, 1, 3))

      println("END")
      exec.shutdown()
   end demoPoolBound
end Demos
