package chap23

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.Executors.global

class QueueTests[Q](using evidence: IntQueue[Q]) extends AnyFunSuite:
   private val M = 1_000_000

   private def testQueue(n: Int) =
      val shared = evidence.newQueue(1.max(n / 2))
      syncForkJoin(1 to n) { id =>
         (M / n) times {
            shared.put(id)
            val e = shared.take()
            assert(1 <= e && e <= n)
         }
      }
      // check that queue is empty
      shared.put(0)
      assert(shared.take() == 0)

   test("queue") {
      for n <- 1 to 32 do testQueue(n)
   }
