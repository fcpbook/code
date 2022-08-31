package chap12

import chap12.Section_7.*
import org.scalatest.funsuite.AnyFunSuite

class Section_7_Tests extends AnyFunSuite:
   test("countUp, naturals") {
      assert(countUp(10).take(5) == (10 until 15))
      assert(naturals.take(5) == (0 until 5))
      assert(Variant.naturals.take(5) == (0 until 5))
   }

   test("randomNumbers") {
      val s1 = randomNumbers
      assert(s1.exists(_ != s1.head))
      val s2 = Variant.randomNumbers
      assert(s2.exists(_ != s2.head))
   }
