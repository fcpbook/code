package appendix;

import java.util.concurrent.Semaphore;

class SynchronizationJ {
  static public class SimpleLock {
    private final Semaphore semaphore = new Semaphore(1);
    volatile private Thread owner;

    public void lock() throws InterruptedException {
      semaphore.acquire();
      owner = Thread.currentThread();
    }

    public void unlock() {
      if (owner != Thread.currentThread())
        throw new IllegalStateException("not the lock owner");
      owner = null;
      semaphore.release();
    }
  }
}
