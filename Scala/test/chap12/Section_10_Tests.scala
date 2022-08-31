package chap12

import chap12.Section_10.*
import mocks.Chap12.ExpensiveType
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.{ L, M, R }

class Section_10_Tests extends AnyFunSuite:
   test("LazyInit") {
      ExpensiveType.canCreate = false
      val obj = LazyInit.SomeClass()
      ExpensiveType.canCreate = true
      assert(obj.value eq obj.value)
   }

   test("LazyVal") {
      ExpensiveType.canCreate = false
      val obj = SomeClass()
      ExpensiveType.canCreate = true
      assert(obj.value eq obj.value)
   }

   test("hanoi") {
      assert(hanoi(3, L, M, R) == hanoi3)
      assert(hanoi(20, L, M, R).length == (1 << 20) - 1)
      val moves = hanoi(100, 'L', 'M', 'R')
      assert(moves(999) == ('M', 'L'))
      assert(moves(49) == ('L', 'R'))
   }
