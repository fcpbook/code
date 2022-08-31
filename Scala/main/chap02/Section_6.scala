package chap02

object Section_6:
   object Abs1:
      def abs(x: Int): Int =
         def max(a: Int, b: Int): Int = if a > b then a else b
         max(x, -x)

   object Abs2:
      def abs(x: Int): Int =
         def maxX(a: Int): Int = if a > x then a else x
         maxX(-x)
