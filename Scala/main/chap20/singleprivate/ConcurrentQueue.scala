package chap20.singleprivate

import scala.annotation.unused

//noinspection DuplicatedCode
class ConcurrentQueue[A]:
   private var in, out = List.empty[A]
   private val lock    = Object()

   private def isEmpty: Boolean = lock.synchronized(out.isEmpty && in.isEmpty)

   @unused private def isEmpty_also: Boolean =
      assert(Thread.holdsLock(lock))
      out.isEmpty && in.isEmpty

   def put(value: A): Unit = lock.synchronized(in ::= value)

   def take(): Option[A] = lock.synchronized {
      if isEmpty then None
      else
         if out.isEmpty then
            out = in.reverse
            in = List.empty
         val first = out.head
         out = out.tail
         Some(first)
   }

   def putAll(values: A*): Unit = lock.synchronized {
      for value <- values do in ::= value
   }

   def drain(): List[A] =
      val (in, out) = lock.synchronized {
         val i = this.in
         val o = this.out
         this.out = List.empty
         this.in = List.empty
         (i, o)
      }
      out ++ in.reverseIterator
end ConcurrentQueue
