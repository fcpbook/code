package appendix

import appendix.ThreadSafe.SafeSet1
import appendix.ThreadSafe.SafeSet2
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreadSafeTests {
  @Test
  fun testSafeSet1() {
    testSafeSet(1_000_000, SafeSet1.SafeSet<Int>(), { set, x -> set.add(x) }, { it.all() })
  }

  @Test
  fun testSafeSet2() {
    testSafeSet(10_000, SafeSet2.SafeSet<Int>(), { set, x -> set.add(x) }, { it.all() })
  }

  private fun <A> testSafeSet(n: Int, set: A, add: (A, Int) -> Int, all: (A) -> Set<Int>) {
    val exec = Executors.newCachedThreadPool()
    val nums = (1..n).toSet()
    val start = CountDownLatch(2)

    val f1 = exec.submit {
      start.countDown()
      start.await()
      for (num in 1..n)
        assertEquals(num, add(set, num))
    }

    val f2 = exec.submit {
      start.countDown()
      start.await()
      while (true) {
        val s = all(set)
        assert(nums.containsAll(s))
        if (s.size == nums.size) break
      }
    }

    exec.shutdown()
    f1.get()
    f2.get()
  }
}
