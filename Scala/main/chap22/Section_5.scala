package chap22

import mocks.Chap22.{ v0, v1, v2 }

import java.util.concurrent.CountDownLatch
import scala.concurrent.duration.SECONDS

//noinspection TypeAnnotation,DuplicatedCode
object Section_5:
   class Demo:
      private var x = v0
      private val y = Object()

      val t1: Thread = Thread(() => run1(), "T1")
      val t2: Thread = Thread(() => run2(), "T2")
      val t3: Thread = Thread(() => run3(), "T3")

      def run1() = // behavior of thread 1
         SECONDS.sleep(4)
         println(x)
         SECONDS.sleep(5)
         y.synchronized {
            SECONDS.sleep(1)
            println(x)
            SECONDS.sleep(2)
         }

      def run2() = // behavior of thread 2
         x = v1
         SECONDS.sleep(2)
         t3.start()
         SECONDS.sleep(3)
         y.synchronized {
            SECONDS.sleep(2)
            x = v2
            SECONDS.sleep(1)
         }

      def run3() = // behavior of thread 3
         SECONDS.sleep(1)
         println(x)
         SECONDS.sleep(11)
         println(x)
   end Demo

   class SafeBox[A]:
      private var contents = Option.empty[A]
      private val filled   = CountDownLatch(1)

      def get: A =
         filled.await()
         contents.get

      def set(value: A): Boolean = synchronized {
         if contents.nonEmpty then false
         else
            contents = Some(value)
            filled.countDown()
            true
      }
   end SafeBox
