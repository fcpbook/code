package chap09

import chap09.Section_1.*
import mocks.Chap09.Project
import org.scalatest.funsuite.AnyFunSuite

class Section_1_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1.contains(78))
      assert(demo2.isEmpty)
      assert(demo3.contains(91))
      assert(demo4.contains(88))
      assert(demo5.contains(91))
      assert(demo6.contains(Project(12345L)))
      assert(demo7.contains(91))
      assert(demo8.contains(Project(12345L)))
      assert(demo9.contains(91))
      assert(demo10.contains(88))
      assert(demo11.contains(Project(12345L)))
      assert(demo12.isEmpty)
   }
