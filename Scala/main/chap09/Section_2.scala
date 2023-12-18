package chap09

import mocks.Chap09.Project

//noinspection ScalaWeakerAccess
object Section_2:
   def greaterThan(bound: Int)(x: Int): Boolean = x > bound

   def hasID(identity: Long)(project: Project): Boolean = project.id == identity

   object PlusNotCurried:
      def plus(x: Int, y: Int): Int = x + y      // a function of type (Int, Int) => Int
      def demo: Int                 = plus(5, 3) // 8

   object PlusCurried:
      def plus(x: Int)(y: Int): Int = x + y      // a function of type Int => (Int => Int)
      val demo1: Int => Int         = plus(5)    // a function of type Int => Int
      def demo2: Int                = plus(5)(3) // 8

   def lengthBetween(low: Int)(high: Int)(str: String): Boolean =
      str.length >= low && str.length <= high

   def demo1: Boolean = lengthBetween(1)(5)("foo") // true

   val lengthBetween1AndBound: Int => String => Boolean = lengthBetween(1)
   val lengthBetween1and5: String => Boolean            = lengthBetween(1)(5)

   def demo2: Boolean = lengthBetween1AndBound(5)("foo") // true
   def demo3: Boolean = lengthBetween1and5("foo")        // true

   def demo4(): Unit =
      println({
         val two = 2
         two + two
      }) // prints 4

   def demo5(): Unit =
      println {
         val two = 2
         two + two
      } // prints 4

   def demo6: Int =
      import PlusCurried.plus
      plus(5) {
         val two = 2
         two + 1
      }
