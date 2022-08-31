package chap08

import chap08.Section_2.*
import chap08.Section_2.BinTree.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("functional BinTree") {
      assert(!isEmpty(Node(1, Empty, Empty)))
      assert(isEmpty(Empty))
   }
