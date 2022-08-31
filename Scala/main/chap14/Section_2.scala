package chap14

import chap14.simpletrampoline.*

object Section_2:
   object WithoutImplicit:
      def isEven(n: Int): Computation[Boolean] =
         if n == 0 then Done(true) else Call(() => isOdd(n - 1))

      def isOdd(n: Int): Computation[Boolean] =
         if n == 0 then Done(false) else Call(() => isEven(n - 1))
   end WithoutImplicit

   def isEven(n: Int): Computation[Boolean] = if n == 0 then true else call(isOdd(n - 1))
   def isOdd(n: Int): Computation[Boolean]  = if n == 0 then false else call(isEven(n - 1))

   def demo: Boolean = isEven(1_000_000).result // true
