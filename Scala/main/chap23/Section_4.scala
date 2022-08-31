package chap23

import java.util.concurrent.locks.ReentrantLock
import scala.collection.mutable

object Section_4:
   object WaitNotifyAll:
      class BoundedQueue[A](capacity: Int):
         private val queue = mutable.Queue.empty[A]

         def take(): A = synchronized {
            while queue.isEmpty do wait()
            notifyAll()
            queue.dequeue()
         }

         def put(element: A): Unit = synchronized {
            while queue.length == capacity do wait()
            notifyAll()
            queue.enqueue(element)
         }
      end BoundedQueue

   class BoundedQueue[A](capacity: Int):
      private val queue           = mutable.Queue.empty[A]
      private val lock            = ReentrantLock()
      private val canPut, canTake = lock.newCondition()

      def take(): A =
         lock.lock()
         try
            while queue.isEmpty do canTake.await()
            canPut.signal()
            queue.dequeue()
         finally lock.unlock()

      def put(element: A): Unit =
         lock.lock()
         try
            while queue.length == capacity do canPut.await()
            canTake.signal()
            queue.enqueue(element)
         finally lock.unlock()
   end BoundedQueue
