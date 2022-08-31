package chap06

object Section_2:
   def factorial(n: Int): Int = if n == 0 then 1 else n * factorial(n - 1)

   def hanoi[A](n: Int, from: A, middle: A, to: A): Unit =
      if n > 0 then
         hanoi(n - 1, from, to, middle)
         println(s"$from -> $to")
         hanoi(n - 1, middle, from, to)
