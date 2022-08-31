package chap04

import chap04.Section_3.*
import chap04.activepassive.{ functional, immutable }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.CharLetters.*

class Section_3_Tests extends AnyFunSuite:
   private val elements = Set(A, B, C, D, E)

   test("demo1") {
      import functional.*
      var ap = createActivePassive(elements)
      ap = demo1(ap)
      assert(allActive(ap) == Set(C, D, E))
      assert(allPassive(ap) == Set(A, B))
   }

   test("demo2") {
      var ap = immutable.ActivePassive(elements)
      ap = demo2(ap)
      assert(ap.allActive == Set(C, D, E))
      assert(ap.allPassive == Set(A, B))
   }
