package chap05

import chap05.Section_7.*
import org.scalatest.funsuite.AnyFunSuite

class Section_7_Tests extends AnyFunSuite:
   test("ARGB") {
      assert(Demo1.a == 0xAB)
      assert(Demo1.r == 0xCD)
      assert(Demo1.g == 0xEF)
      assert(Demo1.b == 0x12)
   }

   test("Phone") {
      assert(demo2.contains("(603) 555-1234"))
      assert(demo3.contains("(603) 555-1234"))
      assert(demo4.isEmpty)
   }
