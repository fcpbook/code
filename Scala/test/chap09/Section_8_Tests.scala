package chap09

import chap09.Section_1.temps
import chap09.Section_8.*
import org.scalatest.funsuite.AnyFunSuite

class Section_8_Tests extends AnyFunSuite:
   test("findLargerThan90") {
      assert(findGreaterThan90(temps).contains(91))
   }
