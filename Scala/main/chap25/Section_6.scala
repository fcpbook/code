package chap25

import java.util.concurrent.{ Future, FutureTask }
import scala.collection.concurrent.TrieMap

object Section_6:
   object MemoBad:
      // DON'T DO THIS!
      def memo[A, B](f: A => B): A => B =
         val store = TrieMap.empty[A, B]
         x => store.getOrElseUpdate(x, f(x))

   def memo[A, B](f: A => B): A => B =
      val store = TrieMap.empty[A, Future[B]]
      x =>
         val future = store.get(x) match
            case Some(future1) => future1
            case None =>
               val task = FutureTask(() => f(x))
               store.putIfAbsent(x, task) match
                  case Some(future2) => future2
                  case None =>
                     task.run()
                     task
         future.get()
