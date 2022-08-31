package chap27

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import scala.compiletime.uninitialized

object Section_1:
   class SafeBox[A]:
      private var contents: A = uninitialized
      private val filled      = CountDownLatch(1)
      private val isSet       = AtomicBoolean(false)

      def get: A =
         filled.await()
         contents

      def set(value: A): Boolean =
         if isSet.get || isSet.getAndSet(true) then false
         else
            contents = value
            filled.countDown()
            true
   end SafeBox
