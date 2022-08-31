package chap06

import chap06.Section_3.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_3_Tests extends AnyFunSuite:
   test("last") {
      assertThrows[NoSuchElementException](last(List(A, B, C)))
   }

   test("splitAt demo") {
      assert(demo == (List(A, B), List(C, D)))
   }

   test("merge sort") {
      assertThrows[StackOverflowError](mergeSort(List(A, B, C)))
   }

   test("Ackermann") {
      assert(a(1, 2) == 4)
      assert(a(2, 1) == 5)
      assert(a(2, 2) == 7)
   }
