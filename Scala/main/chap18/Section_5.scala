package chap18

import chap12.Section_3.times
import tinyscalautils.text.{ PrintingMode, println }

import java.util.ArrayList

//noinspection ReferenceMustBePrefixed,DuplicatedCode
object Section_5:
   def demo1()(using PrintingMode): Unit =
      println("START")

      val shared = ArrayList[String]()

      def addStrings(n: Int, str: String): Unit =
         n times {
            shared.synchronized(shared.add(str))
         }

      val t1 = Thread(() => addStrings(5, "T1"), "T1")
      val t2 = Thread(() => addStrings(5, "T2"), "T2")

      t1.start(); t2.start()
      t1.join(); t2.join()

      println(shared.size)

      println("END")
   end demo1

   def demo2()(using PrintingMode): Unit =
      println("START")

      val shared = ArrayList[String]()

      def addStrings(n: Int, str: String): Unit = shared.synchronized {
         n times shared.add(str)
      }

      val t1 = Thread(() => addStrings(5, "T1"), "T1")
      val t2 = Thread(() => addStrings(5, "T2"), "T2")

      t1.start(); t2.start()
      t1.join(); t2.join()

      println(shared.size)

      println("END")
   end demo2

   def demo3()(using PrintingMode): Unit =
      println("START")

      val shared = ArrayList[String]()

      def addStrings(n: Int, str: String): Unit = shared.synchronized {
         n times shared.add(str)
      }

      def addAllStrings(n: Int, str: String): Unit = shared.synchronized(addStrings(n, str))

      val t1 = Thread(() => addStrings(5, "T1"), "T1")
      val t2 = Thread(() => addAllStrings(5, "T2"), "T2")

      t1.start(); t2.start()
      t1.join(); t2.join()

      println(shared.size)

      println("END")
   end demo3
