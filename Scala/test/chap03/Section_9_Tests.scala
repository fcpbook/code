package chap03

import chap03.Section_9.*
import mocks.Chap03.{ ImmutableLoad, MutableLoad }
import org.scalatest.funsuite.AnyFunSuite

class Section_9_Tests extends AnyFunSuite:

   test("mutable loads, for/do") {
      val loads = List(MutableLoad(3), MutableLoad(1), MutableLoad(10))
      assert(MutableLoads.reduceAll(loads).map(_.weight) == List(2, 0, 9))
   }

   test("immutable loads, for/yield") {
      val loads = List(ImmutableLoad(3), ImmutableLoad(1), ImmutableLoad(10))
      assert(ImmutableLoads.reduceAll(loads).map(_.weight) == List(2, 0, 9))
   }
