package chap22

import java.util.concurrent.CountDownLatch

//noinspection DuplicatedCode
object Section_3:
   object Deadlock:
      class SafeBox[A]:
         private var contents = Option.empty[A]
         private val filled   = CountDownLatch(1)

         // DON'T DO THIS!
         def get: A = synchronized {
            filled.await()
            contents.get
         }

         def set(value: A): Boolean = synchronized {
            if contents.nonEmpty then false
            else
               contents = Some(value)
               filled.countDown()
               true
         }
      end SafeBox
   end Deadlock

   class SafeBox[A]:
      private var contents = Option.empty[A]
      private val filled   = CountDownLatch(1)

      def get: A =
         filled.await()
         synchronized(contents.get)

      def set(value: A): Boolean = synchronized {
         if contents.nonEmpty then false
         else
            contents = Some(value)
            filled.countDown()
            true
      }
   end SafeBox

   object Variant1:
      class SafeBox[A]:
         private var contents = Option.empty[A]
         private val filled   = CountDownLatch(1)

         def get: A =
            filled.await()
            synchronized(contents.get)

         def set(value: A): Boolean = synchronized {
            if contents.nonEmpty then false
            else
               filled.countDown()
               contents = Some(value)
               true
         }
      end SafeBox
   end Variant1

   object Variant2:
      class SafeBox[A]:
         private var contents = Option.empty[A]
         private val filled   = CountDownLatch(1)

         def get: A =
            filled.await()
            synchronized(contents.get)

         def set(value: A): Boolean =
            val setter = synchronized {
               if contents.nonEmpty then false
               else
                  contents = Some(value)
                  true
            }
            if setter then filled.countDown()
            setter
      end SafeBox
   end Variant2
