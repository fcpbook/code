package chap18

import mocks.Chap18.increment

import java.util.concurrent.atomic.AtomicInteger

object Section_3:
   class Demo1:
      private val userCount = AtomicInteger(0)

      // DON'T DO THIS!
      def getRank(): Int =
         userCount.increment()
         // Thread.`yield`()
         userCount.get
   end Demo1

   class Demo2:
      private val userCount = AtomicInteger(0)

      def getRank(): Int = userCount.incrementAndGet()
   end Demo2

   class Demo3:
      private val userCount = AtomicInteger(0)

      // DON'T DO THIS!
      def getRank(): Option[Int] =
         if userCount.get < 5 then
            // Thread.`yield`()
            Some(userCount.incrementAndGet())
         else None
   end Demo3
