package chap09

import chap09.Section_1.greaterThan

import java.util.logging.Logger
import scala.collection.mutable

//noinspection TypeAnnotation
object Section_7:
   val gt5   = greaterThan(5)
   val gt100 = greaterThan(100)

   def demo1: Boolean = gt5(90)   // true
   def demo2: Boolean = gt100(90) // false

   def memo[A, B](f: A => B): A => B =
      val store = mutable.Map.empty[A, B]

      def g(x: A): B =
         store.get(x) match
            case Some(y) => y
            case None =>
               val y = f(x)
               store(x) = y
               y

      g
   end memo

   val memoLength: String => Int = memo(str => str.length)

   def demo3: Int = memoLength("foo") // invokes "foo".length and returns 3
   def demo4: Int = memoLength("foo") // returns 3, without invoking method length

   def logging[A, B](name: String)(f: A => B): A => B =
      var count  = 0
      val logger = Logger.getLogger("my.package")

      def g(x: A): B =
         count += 1
         logger.info(s"calling $name ($count) with $x")
         val y = f(x)
         logger.info(s"$name($x)=$y")
         y

      g
   end logging

   def demo5(): Unit =
      val lenLog: String => Int = logging("length")(str => str.length)

      lenLog("foo")
      // INFO: calling length (1) with foo
      // INFO: length(foo)=3

      lenLog("bar")
      // INFO: calling length (2) with bar
      // INFO: length(bar)=3lenLog("foo")
   end demo5

   // DON'T DO THIS!
   val multipliers = Array.ofDim[Int => Int](10)

   var n = 0
   while n < 10 do
      multipliers(n) = x => x * n
      n += 1

   val m3 = multipliers(3)

   def demo6: Int = m3(100) // 1000, not 300
