package chap17

import tinyscalautils.text.{ PrintingMode, println }

//noinspection DuplicatedCode
object Section_2:
   def demo()(using PrintingMode): Unit =
      println("START")

      val tA = Thread(() => println('A'), "printerA")
      val tB = Thread(() => println('B'), "printerB")
      val tC = Thread(() => println('C'), "printerC")

      tA.start()
      tB.start()
      tC.start()

      println("END")
   end demo
