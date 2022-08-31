package chap20

import chap20.singlepublic.ConcurrentQueue

object Section_2:
   def putAll[A](queue: ConcurrentQueue[A], values: A*): Unit =
      queue.synchronized {
         for value <- values do queue.put(value)
      }

   def drain[A](queue: ConcurrentQueue[A]): List[A] =
      val buffer = List.newBuilder[A]
      queue.synchronized {
         while !queue.isEmpty do buffer += queue.take().get
      }
      buffer.result()

   object Variant:
      def drain[A](queue: ConcurrentQueue[A]): List[A] =
         val buffer = List.newBuilder[A]
         var option = queue.take()
         while option.nonEmpty do
            buffer += option.get
            option = queue.take()
         buffer.result()
