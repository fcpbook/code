package chap23

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.Executors.global

open class LockSemaphoreTests[A](theLock: A, lock: A => Unit, unlock: A => Unit) extends AnyFunSuite:
   private val M = 1_000_000

   test("lock") {
      def testLock(n: Int) =
         var shared = 0
         syncForkJoin(1 to n) { _ =>
            M times {
               lock(theLock)
               shared += 1
               unlock(theLock)
            }
         }
         assert(shared == n * M)

      for n <- 1 to 32 do testLock(n)
   }
