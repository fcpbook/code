package appendix;

import java.util.Set;

class ThreadSafeJ {
  static public class SafeSet<A> {
    private final Set<A> elements = new java.util.HashSet<>();

    public synchronized int add(A elem) {
      elements.add(elem);
      return elements.size();
    }

    public synchronized Set<A> all() {
      return Set.copyOf(elements); // full copy here
    }
  }
}
