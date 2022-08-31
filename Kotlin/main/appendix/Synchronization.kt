package appendix

import java.util.concurrent.Semaphore

object Synchronization {
  class SimpleLock {
    private val semaphore = Semaphore(1)

    @Volatile
    private var owner: Thread? = null

    fun lock() {
      semaphore.acquire()
      owner = Thread.currentThread()
    }

    fun unlock() {
      if (owner != Thread.currentThread())
        throw IllegalStateException("not the lock owner")
      owner = null
      semaphore.release()
    }
  }
}
