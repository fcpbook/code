package chap09

import chap09.Section_6.*
import mocks.Chap09.Project
import org.scalatest.funsuite.AnyFunSuite

class Section_6_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1.contains(33))
      assert(demo2.contains(33))
      assert(demo3.contains(91))
      assert(demo4.contains(Project(12345L)))
   }
