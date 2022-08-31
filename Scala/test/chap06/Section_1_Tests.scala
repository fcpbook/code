package chap06

import chap06.Section_1.*
import mocks.Chap06.dump
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

import scala.collection.mutable

class Section_1_Tests extends AnyFunSuite:
   test("loop 1") {
      import MutableLoop.processCollection
      dump.clear()
      processCollection(mutable.Set(A, B, C))
      assert(dump == Seq(A, B, C))
   }

   test("loop 2") {
      import ImmutableLoop.processCollection
      dump.clear()
      processCollection(Set(A, B, C))
      assert(dump == Seq(A, B, C))
   }

   test("recursion") {
      import ImmutableRec.processCollection
      dump.clear()
      processCollection(Set(A, B, C))
      assert(dump == Seq(A, B, C))
   }
