package chap23

import chap12.Section_3.times
import chap19.Section_5.SafeStringList

import java.util.concurrent.{ CountDownLatch, CyclicBarrier, Executors }

//noinspection DuplicatedCode
object Section_2:
   def demo1(N: Int): Unit =
      val exec   = Executors.newCachedThreadPool()
      val shared = SafeStringList()
      val start  = CountDownLatch(N + 1)
      val finish = CountDownLatch(N)

      for i <- 1 to N do
         exec.execute { () =>
            start.countDown()
            start.await()
            5 times shared.add(i.toString)
            finish.countDown()
         }

      start.countDown()
      start.await()
      val time1 = System.nanoTime()
      finish.await()
      val time2 = System.nanoTime()

      exec.shutdown()

      assert(shared.size == 5 * N)
      assert((1 to N).forall(i => shared.getAll.count(_ == i.toString) == 5))
      assert((time2 - time1) / 1E9 <= 0.01)
   end demo1

   def demo2(N: Int): Unit =
      val exec     = Executors.newCachedThreadPool()
      val shared   = SafeStringList()
      val startEnd = CyclicBarrier(N + 1)

      for i <- 1 to N do
         exec.execute { () =>
            startEnd.await()
            5 times shared.add(i.toString)
            startEnd.await()
         }

      startEnd.await()
      val time1 = System.nanoTime()
      startEnd.await()
      val time2 = System.nanoTime()

      exec.shutdown()

      assert(shared.size == 5 * N)
      assert((1 to N).forall(i => shared.getAll.count(_ == i.toString) == 5))
      assert((time2 - time1) / 1E9 <= 0.1)
   end demo2
