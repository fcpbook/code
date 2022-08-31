package chap17

import tinyscalautils.text.{ PrintingMode, println }

//noinspection TypeAnnotation,DuplicatedCode
object Section_4:
   def demo1(): Unit =
      object task extends Runnable:
         @volatile private var continue = true

         def terminate() = continue = false

         def run() =
            while continue do () // perform task
            end while
            // cleanup
      end task

      val runner = Thread(task, "Runner")
      runner.start()
      // other code while runner is running
      task.terminate()

      runner.join(500)          // timeout in milliseconds
      if runner.isAlive then () // handle timeout case
      else ()                   // runner is terminated
   end demo1

   def demo2()(using PrintingMode): Unit =
      println("START")

      val tA = Thread(() => println('A'), "printerA")
      val tB = Thread(() => println('B'), "printerB")
      val tC = Thread(() => println('C'), "printerC")

      tA.start()
      tB.start()
      tC.start()

      tA.join()
      tB.join()
      tC.join()

      println("END")
   end demo2
