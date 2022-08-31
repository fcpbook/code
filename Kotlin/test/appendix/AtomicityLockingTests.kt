package appendix

import appendix.AtomicityLocking.Rank1
import appendix.AtomicityLocking.Rank2
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.test.Test
import kotlin.test.assertEquals

class AtomicityLockingTests {
  @Test
  fun testGetRank1() {
    testGetRank({ Rank1() }, { it.getRank() })
  }

  @Test
  fun testGetRank2() {
    testGetRank({ Rank2() }, { it.getRank() })
  }

  private fun <A> testGetRank(make: () -> A, getRank: (A) -> Int?) {
    val n = max(Runtime.getRuntime().availableProcessors(), 6)
    val exec = Executors.newCachedThreadPool()
    repeat(10_000) {
      val start = CountDownLatch(n)
      val rank = make()
      val tasks = List(n) {
        Callable {
          start.countDown()
          start.await()
          getRank(rank)
        }
      }
      val values = exec.invokeAll(tasks).map { it.get() }.toSet()
      assertEquals(setOf(1, 2, 3, 4, 5, null), values)
    }
    exec.shutdown()
  }
}
