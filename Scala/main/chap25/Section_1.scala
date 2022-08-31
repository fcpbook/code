package chap25

import java.util.concurrent.Executors
import scala.concurrent.duration.MINUTES

object Section_1:
   def demo(assert: Boolean => Unit): Unit =
      val exec = Executors.newCachedThreadPool()

      def makeStrings(n: Int, str: String): List[String] = List.fill(n)(str)

      var strings1: List[String] = null
      var strings2: List[String] = null

      exec.execute(() => strings1 = makeStrings(5, "T1"))
      exec.execute(() => strings2 = makeStrings(5, "T2"))

      exec.shutdown()
      exec.awaitTermination(5, MINUTES)

      val strings: List[String] = strings1 ::: strings2
      assert(strings.size == 10)
   end demo
