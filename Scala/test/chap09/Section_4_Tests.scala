package chap09

import chap09.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1.contains(91))
      assert(demo2.contains(91))
   }
