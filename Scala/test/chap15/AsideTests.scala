package chap15

import chap15.Aside.*
import org.scalatest.funsuite.AnyFunSuite

class AsideTests extends AnyFunSuite:
   test("inheritance") {
      val s = Inheritance.S()
      assert(s.service1 == 10)
      assert(s.service2("foo") == 3)
      assert(s.service3(42) == "[52]")
      assert(s.service4 == math.Pi)
   }

   test("composition") {
      val s = Composition.S()
      assert(s.service1 == 10)
      assert(s.service3(42) == "[52]")
      assert(s.service4 == math.Pi)
      assertDoesNotCompile("""s.service2("foo")""")
   }
