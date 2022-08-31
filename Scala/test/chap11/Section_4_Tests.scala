package chap11

import chap11.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("demo") {
      assertResult("""Root/
                     |.File1
                     |.Dir1/
                     |..File2
                     |..Dir3/
                     |.Dir2/
                     |..File3
                     |""".stripMargin) {
         demo.mkString(".")
      }
   }
