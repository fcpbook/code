package chap17

import tinyscalautils.text.{ PrintingMode, println }

//noinspection DuplicatedCode
object Section_1:
   def demo1(): Unit =
      import tinyscalautils.text.{ println, threadTimeDemoMode }

      println("START")
      println("END")
   end demo1

   def demo2()(using PrintingMode): Unit =
      println("START")

      class LetterPrinter(letter: Char) extends Runnable:
         def run(): Unit = println(letter)

      val tA = Thread(LetterPrinter('A'))
      val tB = Thread(LetterPrinter('B'))
      val tC = Thread(LetterPrinter('C'))

      tA.start()
      tB.start()
      tC.start()

      println("END")
   end demo2

   def demo3()(using PrintingMode): Unit =
      println("START")

      class LetterPrinter(letter: Char) extends Runnable:
         def run(): Unit = println(letter)

      val tA = Thread(LetterPrinter('A'), "printerA")
      val tB = Thread(LetterPrinter('B'), "printerB")
      val tC = Thread(LetterPrinter('C'), "printerC")

      tA.start()
      tB.start()
      tC.start()

      println("END")
   end demo3
