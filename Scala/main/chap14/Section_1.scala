package chap14

import scala.annotation.tailrec

//noinspection DfaConstantConditions
object Section_1:
   @tailrec
   def zero(x: Int): Int = if x == 0 then 0 else zero(x - 1)

   def demo1: Int = zero(1_000_000) // 0

   def isEven(n: Int): Boolean = if n == 0 then true else isOdd(n - 1)
   def isOdd(n: Int): Boolean  = if n == 0 then false else isEven(n - 1)

   def demo2: Boolean = isEven(42)        // true
   def demo3: Boolean = isOdd(42)         // false
   def demo4: Boolean = isEven(1_000_000) // throws StackOverflowError
