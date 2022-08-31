package appendix

import appendix.Synchronization.SimpleLock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizationTests {
  @Test
  fun testSimpleLock() {
    val n = 32
    val m = 1_000_000
    val exec = Executors.newCachedThreadPool()
    val start = CountDownLatch(n)
    val lock = SimpleLock()
    var shared = 0

    repeat(n) {
      exec.execute {
        start.countDown()
        start.await()
        repeat(m) {
          lock.lock()
          shared += 1
          lock.unlock()
        }
      }
    }
    exec.shutdown()
    exec.awaitTermination(5, MINUTES)
    assertEquals(n * m, shared)
  }
}
