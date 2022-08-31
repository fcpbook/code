package chap22

import chap12.Section_3.times
import chap19.Section_5.SafeStringList
import tinyscalautils.threads.Executors.global as exec

import java.util.concurrent.Executors
import scala.concurrent.duration.{ MILLISECONDS, MINUTES }

//noinspection DuplicatedCode
object Section_1:
   def demo1(assert: Boolean => Unit): Unit =
      val exec   = Executors.newCachedThreadPool()
      val shared = SafeStringList()

      def addStrings(n: Int, str: String): Unit = n times shared.add(str)

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      exec.shutdown()
      exec.awaitTermination(5, MINUTES)

      assert(shared.size == 10)
   end demo1

   def demo2(): Unit =
      val shared = SafeStringList()

      def addStrings(n: Int, str: String): Unit = n times shared.add(str)

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      // DON'T DO THIS!
      MILLISECONDS.sleep(10)

      assert(shared.size == 10)
   end demo2

   @main def TestSleepDemo(): Unit = demo2() // may fail

   def demo3(): Unit =
      val shared     = SafeStringList()
      var terminated = 0

      def addStrings(n: Int, str: String): Unit =
         n times shared.add(str)
         terminated += 1

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      // DON'T DO THIS!
      while terminated < 2 do ()

      assert(shared.size == 10)
   end demo3

   @main def TestBusyWaiting1Demo(): Unit = demo3() // may not terminate

   def demo4(assert: Boolean => Unit): Unit =
      val shared     = SafeStringList()
      var terminated = 0
      val lock       = Object()

      def addStrings(n: Int, str: String): Unit =
         n times shared.add(str)
         lock.synchronized(terminated += 1)

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      // DON'T DO THIS!
      while lock.synchronized(terminated) < 2 do ()

      assert(shared.size == 10)
   end demo4

   def demo5(assert: Boolean => Unit): Unit =
      val shared     = SafeStringList()
      var terminated = 0
      val lock       = Object()

      def addStrings(n: Int, str: String): Unit =
         n times shared.add(str)
         lock.synchronized(terminated += 1)

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      // DON'T DO THIS!
      while lock.synchronized(terminated) < 2 do MILLISECONDS.sleep(5)

      assert(shared.size == 10)
   end demo5
