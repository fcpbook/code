package chap12

import mocks.Chap12.ExpensiveType

object Section_10:
   // noinspection ConvertNullInitializerToUnderscore
   object LazyInit:
      class SomeClass:
         private var theValue: ExpensiveType = null
         def value: ExpensiveType =
            if theValue eq null then theValue = ExpensiveType.create()
            theValue

   class SomeClass:
      lazy val value: ExpensiveType = ExpensiveType.create()

   def hanoi[A](n: Int, from: A, mid: A, to: A): LazyList[(A, A)] =
      if n == 0 then LazyList.empty
      else
         lazy val before = hanoi(n - 1, from, to, mid)
         lazy val after  = hanoi(n - 1, mid, from, to)
         before #::: (from, to) #:: after
