package chap12

import java.io.ObjectOutputStream
import java.net.InetAddress
import java.nio.file.{ Files, Path }
import scala.util.Using

//noinspection TypeAnnotation
object Section_3:
   def writeToFile[A](file: Path, values: Iterable[A]): Unit =
      Using.resource(ObjectOutputStream(Files.newOutputStream(file))) { out =>
         for item <- values do out.writeObject(item)
      }

   def resource[R <: AutoCloseable, A](res: R)(use: R => A): A =
      try use(res)
      finally res.close()

   def timeOf[U](code: => U): Double =
      val startTime = System.nanoTime()
      code
      val endTime = System.nanoTime()
      (endTime - startTime) / 1E9

   def seconds: Double = timeOf {
      InetAddress.getLocalHost.getHostName // or any code for which you want the duration
   }

   extension (count: Int)
      infix def times[U](code: => U): Unit =
         var n = count
         while n > 0 do
            code
            n -= 1

   def demo1(): Unit =
      3 times {
         println("Beetlejuice!")
      }

   class Repeat[U](code: => U):
      infix def until(test: => Boolean): Unit =
         code
         while !test do code

   def repeat[U](code: => U) = Repeat(code)

   def demo2(): Unit =
      var n = 3
      repeat {
         println("Beetlejuice!")
         n -= 1
      } until (n == 0)
