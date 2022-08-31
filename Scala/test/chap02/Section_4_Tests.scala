package chap02

import chap02.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

class Section_4_Tests extends AnyFunSuite:
   test("-->") {
      val a, b = Node()
      val edge = a --> b
      assert(edge.from eq a)
      assert(edge.to eq b)
   }

   test("to") {
      val a, b = Node()
      val edge = a to b
      assert(edge.from eq a)
      assert(edge.to eq b)
   }
