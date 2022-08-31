package appendix

import org.scalatest.funsuite.AnyFunSuite

class TypeTests extends AnyFunSuite:
   import Types.*

   test("opaque") {
      assert(oneInch.toString == "0.0254")
      assert(len.toString == "1.0")
      assertTypeError("Opaque.wholeMeters(2.3)")
   }
