package chap18

import chap12.Section_3.times
import tinyscalautils.text.{ PrintingMode, println }

object Section_1:
   def demo()(using PrintingMode): Unit =
      println("START")

      // DON'T DO THIS!
      var shared = 0

      def add(n: Int): Unit = n times (shared += 1)

      val t1 = Thread(() => add(5), "T1")
      val t2 = Thread(() => add(5), "T2")

      t1.start(); t2.start()
      t1.join(); t2.join()

      println(shared)

      println("END")
   end demo
