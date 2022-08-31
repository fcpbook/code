package chap20.singlepublic

import scala.annotation.unused

//noinspection DuplicatedCode
class ConcurrentQueue[A]:
   private var in, out = List.empty[A]

   def isEmpty: Boolean = synchronized(out.isEmpty && in.isEmpty)

   def put(value: A): Unit = synchronized(in ::= value)

   def take(): Option[A] = synchronized {
      if isEmpty then None
      else
         if out.isEmpty then
            out = in.reverse
            in = List.empty
         val first = out.head
         out = out.tail
         Some(first)
   }

   // DON'T DO THIS!
   @unused def take_bad(): Option[A] =
      if isEmpty then None
      else
         synchronized {
            if out.isEmpty then
               out = in.reverse
               in = List.empty
            val first = out.head
            out = out.tail
            Some(first)
         }
end ConcurrentQueue
