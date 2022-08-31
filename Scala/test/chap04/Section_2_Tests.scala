package chap04

import chap04.Section_2.*
import chap04.activepassive.{ functional, mutable1 }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.CharLetters.*

class Section_2_Tests extends AnyFunSuite:
   private val elements = Set(A, B, C, D, E)

   test("demo1") {
      val ap = mutable1.ActivePassive(elements)
      demo1(ap)
      assert(ap.allActive == Set(C, D, E))
      assert(ap.allPassive == Set(A, B))
   }

   test("demo2") {
      import functional.*
      var ap = createActivePassive(elements)
      ap = demo2(ap)
      assert(allActive(ap) == Set(C, D, E))
      assert(allPassive(ap) == Set(A, B))
   }
