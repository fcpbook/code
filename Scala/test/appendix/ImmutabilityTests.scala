package appendix

import org.scalatest.funsuite.AnyFunSuite

class ImmutabilityTests extends AnyFunSuite:
   import Immutability.*
   import ImmutabilityJ.*

   test("tail") {
      assert(b == List("B", "C"))
   }

   test("Java wrapper") {
      assertThrows[UnsupportedOperationException](demo())
   }
