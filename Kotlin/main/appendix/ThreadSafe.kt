package appendix

object ThreadSafe {
  object SafeSet1 {
    class SafeSet<A> {
      private val elements = mutableSetOf<A>()

      @Synchronized
      fun add(elem: A): Int {
        elements += elem
        return elements.size
      }

      @Synchronized
      fun all(): Set<A> = elements.toSet() // full copy here
    }
  }

  object SafeSet2 {
    class SafeSet<A> {
      private var elements = setOf<A>()

      @Suppress("SuspiciousCollectionReassignment")
      @Synchronized
      fun add(elem: A): Int {
        elements += elem // full copy here
        return elements.size
      }

      @Synchronized
      fun all(): Set<A> = elements
    }
  }
}
