package chap10

import chap10.Section_1.temps

import java.io.{ DataOutputStream, Writer }
import scala.collection.immutable.{ BitSet, SortedSet }

object Section_2:
   def demo1: List[Int] =
      temps.map(temp => ((temp - 32) / 1.8f).round) // List(31, 33, 26, 21, 38, 37, 21)

   def demo2: List[String] = temps.map(temp => if temp > 72 then "high" else "low")
   // List("high", "high", "high", "low", "high", "high", "low")

   def demo3: List[(Int, Int)] = temps.map(temp => (72, temp - 72))
   // List((72,16), (72,19), (72,6), (72,-3), (72,28), (72,26), (72,-2))

   def demo4: Set[Double]    = Set(0.12, 0.35, 0.6).map(1.0 - _) // Set(0.88, 0.65, 0.4)
   def demo5: Option[String] = Some("foo").map(_.toUpperCase)    // Some("FOO")

   def demo6: IndexedSeq[Int]   = "foo".map(_.toInt)                // IndexedSeq(102, 111, 111)
   def demo7: SortedSet[Double] = BitSet(12, 35, 60).map(_ / 100.0) // SortedSet(0.12, 0.35, 0.6)

   def demo8(): Unit =
      val out: DataOutputStream = mocks.Chap10.someOut()
      temps.foreach(temp => out.writeInt(temp))

   def demo9: List[Unit] =
      val out: DataOutputStream = mocks.Chap10.someOut()
      temps.map(temp => out.writeInt(temp))

   def demo10(): Unit =
      val writer: Writer = mocks.Chap10.someWriter()
      temps.foreach(temp => writer.append(temp.toString).append('\n'))

   def demo11: List[Writer] =
      val writer: Writer = mocks.Chap10.someWriter()
      temps.map(temp => writer.append(temp.toString).append('\n'))
