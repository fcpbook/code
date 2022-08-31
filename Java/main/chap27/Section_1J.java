package chap27;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater;

class Section_1J {
  static class LockedInteger {
    // THIS IS NOT java.util.concurrent.AtomicInteger!
    private int value = 0;

    public synchronized int get() {
      return value;
    }

    public synchronized int incrementAndGet() {
      value += 1;
      return value;
    }
  }

  static class AtomicInteger {
    private static final AtomicIntegerFieldUpdater<AtomicInteger> updater
        = newUpdater(AtomicInteger.class, "value");

    private volatile int value;

    public int get() {
      return value;
    }

    public int incrementAndGet() {
      while (true) {
        int current = value;
        int next = current + 1;
        if (updater.compareAndSet(this, current, next))
          return next;
      }
    }
  }
}
