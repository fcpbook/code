package appendix;

import java.util.OptionalInt;

class AtomicityLockingJ {
  static class LockObject {
    private final Object lock = new Object();
    private int userCount = 0;

    OptionalInt getRank() {
      synchronized (lock) {
        if (userCount < 5) {
          userCount += 1;
          return OptionalInt.of(userCount);
        }
        return OptionalInt.empty();
      }
    }
  }

  static class LockMethod {
    private int userCount = 0;

    synchronized OptionalInt getRank() {
      if (userCount < 5) {
        userCount += 1;
        return OptionalInt.of(userCount);
      }
      return OptionalInt.empty();
    }
  }

  static class LockThis {
    private int userCount = 0;

    OptionalInt getRank() {
      synchronized (this) {
        if (userCount < 5) {
          userCount += 1;
          return OptionalInt.of(userCount);
        }
        return OptionalInt.empty();
      }
    }
  }
}
