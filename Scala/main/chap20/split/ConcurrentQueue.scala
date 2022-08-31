package chap20.split

import scala.annotation.unused

//noinspection DuplicatedCode
class ConcurrentQueue[A]:
   private var in, out         = List.empty[A]
   private val inLock, outLock = Object()

   def put(value: A): Unit = inLock.synchronized(in ::= value)

   def take(): Option[A] =
      outLock.synchronized {
         out match
            case first :: others => out = others; Some(first)
            case Nil =>
               val in = inLock.synchronized {
                  val i = this.in
                  this.in = List.empty
                  i
               }
               in.reverse match
                  case first :: others => out = others; Some(first)
                  case Nil             => None
      }

   def putAll(values: A*): Unit = inLock.synchronized {
      for value <- values do in ::= value
   }

   def drain(): List[A] =
      val (in, out) =
         outLock.synchronized {
            inLock.synchronized {
               val i = this.in
               val o = this.out
               this.out = List.empty
               this.in = List.empty
               (i, o)
            }
         }
      out ++ in.reverseIterator

   // DON'T DO THIS!
   @unused def drain_bad(): List[A] =
      val (in, out) =
         inLock.synchronized {
            outLock.synchronized {
               val i = this.in
               val o = this.out
               this.out = List.empty
               this.in = List.empty
               (i, o)
            }
         }
      out ++ in.reverseIterator
end ConcurrentQueue
