package chap03

import chap03.Section_8.ImmutableDirectory.*
import chap03.Section_8.MutableDirectory.*
import mocks.Chap03.someMutableDirectory
import org.scalatest.funsuite.AnyFunSuite

class Section_8_Tests extends AnyFunSuite:

   test("demos") {
      assert(Demo1.todayReg == Set("new user 1", "new user 2"))
      assert(Demo1.todayDir != someMutableDirectory ++ Set("new user 1", "new user 2"))
      assert(Demo2.todayReg == Set("new user 1", "new user 2"))
      assert(Demo2.todayDir == someMutableDirectory ++ Set("new user 1", "new user 2"))
      assert(Demo.todayReg == Set("new user 1", "new user 2"))
      assert(Demo.todayDir == someMutableDirectory ++ Set("new user 1", "new user 2"))
   }
