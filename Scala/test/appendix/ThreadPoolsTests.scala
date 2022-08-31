package appendix

import mocks.Appendix.threads
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global

import java.util.concurrent.CountDownLatch

class ThreadPoolsTests extends AnyFunSuite:
   import ThreadPools.*
   import ThreadPoolsJ.*

   private def runTest(code: CountDownLatch => Unit): Thread =
      val latch = CountDownLatch(1)
      threads.clear()
      code(latch)
      latch.await()
      assert(threads.size == 1)
      threads.remove()

   test("execute (Scala)") {
      val thread = runTest(demo1(global, _))
      assert(thread.getName.startsWith("pool-"))
   }

   test("execute (Java)") {
      val thread = runTest(demo(global, _))
      assert(thread.getName.startsWith("pool-"))
   }

   test("future") {
      val thread = runTest(demo2(global, _))
      assert(thread.getName.startsWith("pool-"))
   }

   test("context") {
      val thread = runTest(demo3)
      assert(thread.getName.contains("-global-"))
   }
