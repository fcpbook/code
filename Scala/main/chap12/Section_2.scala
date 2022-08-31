package chap12

import chap12.Section_1.properties

import java.net.InetAddress
import java.util.Properties
import scala.collection.mutable
import scala.util.Random

object Section_2:
   val demo1: List[Int] =
      val rand = Random(42)
      List.fill(5)(rand.nextInt(100)) // List(30, 63, 48, 84, 70)

   def fill[A](len: Int)(value: => A): List[A] =
      val buffer = List.newBuilder[A]
      var i      = 0
      while i < len do
         buffer += value
         i += 1
      buffer.result()

   val demo2: List[Int] =
      val rand = Random(42)
      fill(5)(rand.nextInt(100)) // List(30, 63, 48, 84, 70)

   extension (properties: Properties)
      def getProperty(key: String, fallback: () => String): String =
         Option(properties.getProperty(key)).getOrElse(fallback())

   def demo3: String =
      properties.getProperty("hostname", () => InetAddress.getLocalHost.getHostName)

   def memo[A, B](f: A => B): A => B =
      val store = mutable.Map.empty[A, B]
      x => store.getOrElseUpdate(x, f(x))
