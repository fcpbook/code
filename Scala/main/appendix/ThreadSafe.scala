package appendix

object ThreadSafe:
   class SafeSet[A]:
      private var elements = Set.empty[A]

      def += (elem: A): Int = synchronized {
         elements += elem
         elements.size
      }

      def all: Set[A] = synchronized(elements)
   end SafeSet
end ThreadSafe
