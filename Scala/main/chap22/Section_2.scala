package chap22

import chap12.Section_3.times
import chap19.Section_5.SafeStringList
import tinyscalautils.threads.Executors.global as exec

import java.util.concurrent.CountDownLatch

object Section_2:
   def demo(assert: Boolean => Unit): Unit =
      val shared = SafeStringList()
      val latch  = CountDownLatch(2)

      def addStrings(n: Int, str: String): Unit =
         n times shared.add(str)
         latch.countDown()

      exec.execute(() => addStrings(5, "T1"))
      exec.execute(() => addStrings(5, "T2"))

      latch.await()

      assert(shared.size == 10)
   end demo
