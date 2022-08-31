package appendix

import kotlinx.coroutines.sync.Semaphore
import java.util.concurrent.Executors
import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicInteger

object MinimizingBlocking {
  class CountDownLatch(count: Int) {
    init {
      require(count > 0)
    }

    private val remaining = AtomicInteger(count)
    private val semaphore = Semaphore(permits = 1, acquiredPermits = 1)

    suspend fun await() {
      if (remaining.get() > 0) {
        semaphore.acquire()
        semaphore.release()
      }
    }

    fun countDown() {
      if (remaining.get() > 0 && remaining.decrementAndGet() == 0)
        semaphore.release()
    }
  }

  suspend fun demo(N: Int, M: Int): ExecutorService {
    val exec = Executors.newFixedThreadPool(N)
    val latch = CountDownLatch(M)

    withContext(exec.asCoroutineDispatcher()) {
      for (id in 1..M) {
        launch {
          latch.countDown()
          latch.await()
        }
      }
    }

    return exec
  }
}
