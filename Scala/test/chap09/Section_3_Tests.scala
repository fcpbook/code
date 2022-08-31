package chap09

import chap09.Section_3.*
import mocks.Chap09.{ Project, date1, projects }
import org.scalatest.funsuite.AnyFunSuite

class Section_3_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1(42) == 43)
      assert(demo2.contains(91))
      assert(demo3.contains(Project(12345)))
      assert(demo4.contains(91))
      assert(demo5.contains(Project(12345)))
      assert(demo6.contains((date1, 93)))
      assert(demo7.contains((date1, 93)))
      assert(demo8.contains((Some(date1), 91)))
      assert(demo9((10, 20)) + 1 == 11)
      assert(demo9(("egg", "chicken")).toUpperCase == "EGG")
   }

   test("greaterThan") {
      assert(DeclaredTypes.greaterThan(10)(11))
      assert(!DeclaredTypes.greaterThan(11)(10))
      assert(greaterThan(10)(11))
      assert(!greaterThan(11)(10))
   }

   test("hasID") {
      assert(DeclaredTypes.hasID(11111L)(projects.head))
      assert(!DeclaredTypes.hasID(12345L)(projects.head))
      assert(hasID(11111L)(projects.head))
      assert(!hasID(12345L)(projects.head))
   }
