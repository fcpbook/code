package chap10

import chap10.Section_9.*
import mocks.Chap03.{ ImmutableLoad, MutableLoad }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

import scala.jdk.StreamConverters.IntStreamHasToScala

class Section_9_Tests extends AnyFunSuite:
   private val iLoads = List(ImmutableLoad(3), ImmutableLoad(1), ImmutableLoad(0))

   private def loads = List(MutableLoad(3), MutableLoad(1), MutableLoad(10))

   test("demo1") {
      assertResult(Seq(None, None, None, Some(0))) {
         (0 to 3).map(demo1(_, 0))
      }
   }

   test("demo2/3") {
      assert(demo2(iLoads) == demo3(iLoads))
   }

   test("demo4/5") {
      val loads1, loads2 = loads
      assert(demo4(loads1) == demo5(loads2))
   }

   test("demo6/7") {
      assert(demo6(iLoads) == demo7(iLoads))
   }

   test("demo8") {
      assert(printout(demo8()) == "1\n2\n3\n4\n5\n")
   }

   test("demo9") {
      assert(demo9 == List(31, 33, 26, 38, 37))
   }

   test("Java demo") {
      assert(Section_9J.demo().toScala(Seq) == Seq(31, 33, 26, 38, 37))
   }
