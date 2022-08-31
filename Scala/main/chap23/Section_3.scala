package chap23

import java.util.concurrent.Semaphore
import scala.collection.mutable

object Section_3:
   class SimpleLock:
      private val semaphore       = Semaphore(1)
      @volatile private var owner = Option.empty[Thread]

      def lock(): Unit =
         semaphore.acquire()
         owner = Some(Thread.currentThread)

      def unlock(): Unit =
         if !owner.contains(Thread.currentThread) then
            throw IllegalStateException("not the lock owner")
         owner = None
         semaphore.release()
   end SimpleLock

   class BoundedQueue[A](capacity: Int):
      private val queue   = mutable.Queue.empty[A]
      private val canPut  = Semaphore(capacity)
      private val canTake = Semaphore(0)

      def take(): A =
         canTake.acquire()
         val element = synchronized(queue.dequeue())
         canPut.release()
         element

      def put(element: A): Unit =
         canPut.acquire()
         synchronized(queue.enqueue(element))
         canTake.release()
   end BoundedQueue
