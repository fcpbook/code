package chap10

import chap10.Section_1.temps
import chap10.Section_2.*
import mocks.Chap10.{ currentWriter, outBytes, writerString }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.lang.unit

import java.io.{ ByteArrayInputStream, DataInputStream }
import scala.collection.immutable.SortedSet

class Section_2_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == List(31, 33, 26, 21, 38, 37, 21))
      assert(demo2 == List("high", "high", "high", "low", "high", "high", "low"))
      assert(demo3 == List((72, 16), (72, 19), (72, 6), (72, -3), (72, 28), (72, 26), (72, -2)))
      assert(demo4 == Set(0.88, 0.65, 0.4))
      assert(demo5.contains("FOO"))
      assert(demo6 == IndexedSeq(102, 111, 111))
      assert(demo7 == SortedSet(0.12, 0.35, 0.6))
   }

   private def checkBytes() =
      assert(outBytes.nonEmpty)
      val in = DataInputStream(ByteArrayInputStream(outBytes.get))
      for temp <- temps do assert(in.readInt() == temp)
      assert(in.read() == -1)

   test("demo8") {
      demo8()
      checkBytes()
   }

   test("demo9") {
      assert(demo9 == List.fill(temps.length)(unit))
      checkBytes()
   }

   test("demo10") {
      demo10()
      assert(writerString.contains(temps.mkString("", "\n", "\n")))
   }

   test("demo11") {
      assert(demo11 == List.fill(temps.length)(currentWriter.get))
      assert(writerString.contains(temps.mkString("", "\n", "\n")))
   }
