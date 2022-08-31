package chap09

import chap09.Section_5.Formatter
import mocks.Chap09.given

//noinspection VarCouldBeVal,DfaConstantConditions,ScalaUnusedSymbol
object Aside:
   object Demo1:
      var str: String = ""

      def f(x: Int): Int =
         if x > 0 then
            val str: Int = x - 1
            str + 1
         else
            val x: String = str.toUpperCase
            x.length
   end Demo1

   def demo2(): Unit =
      var x = 1

      def f() =
         var x = 2
         if x > 0 then
            var x = 3
            var y = 4
         println(x) // prints 2
      // println(y) // rejected at compile-time

      f()
      println(x) // prints 1
   // println(y) // rejected at compile-time
   end demo2

   def demo3(): Unit =
      var x = 1

      def f() =
         x += 1
         println(x) // prints 2

      def g() =
         var x = 10
         f()

      g()
      println(x) // prints 2
   end demo3

   def printFormatted(any: Any)(using formatter: Formatter): Unit =
      formatter.println(any)

   def demo4(): Unit = printFormatted("foo")

   def demo5(): Unit =
      given UpperCaseFormatter: Formatter = str => str.toUpperCase
      printFormatted("foo")
