package chap04.activepassive

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.CharLetters.*

class ActivePassiveTests extends AnyFunSuite:
   private val elements = Set(A, B, C, D, E)

   test("mutable 1") {
      val ap = mutable1.ActivePassive(elements)
      for e <- elements do
         assert(!ap.isActive(e))
         assert(ap.isPassive(e))
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(!ap.isAllActive)
      assert(ap.isAllPassive)

      assertThrows[IllegalArgumentException](ap.activate(X))
      assertThrows[IllegalArgumentException](ap.deactivate(X))

      ap.activate(D)
      ap.activate(C)
      ap.deactivate(D)
      ap.activate(E)
      assert(ap.isPassive(A))
      assert(ap.isPassive(B))
      assert(ap.isActive(C))
      assert(ap.isPassive(D))
      assert(ap.isActive(E))
      assert(!ap.isAllActive)
      assert(!ap.isAllPassive)
      assert(ap.allActive == Set(C, E))
      assert(ap.allPassive == Set(A, B, D))
      ap.activateAll()
      assert(ap.allActive == elements)
      assert(ap.allPassive.isEmpty)
      assert(ap.isAllActive)
      ap.deactivateAll()
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(ap.isAllPassive)
   }

   test("mutable 2") {
      val ap = mutable2.ActivePassive(elements)
      for e <- elements do
         assert(!ap.isActive(e))
         assert(ap.isPassive(e))
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(!ap.isAllActive)
      assert(ap.isAllPassive)

      assertThrows[IllegalArgumentException](ap.activate(X))
      assertThrows[IllegalArgumentException](ap.deactivate(X))

      ap.activate(D)
      ap.activate(C)
      ap.deactivate(D)
      ap.activate(E)
      assert(ap.isPassive(A))
      assert(ap.isPassive(B))
      assert(ap.isActive(C))
      assert(ap.isPassive(D))
      assert(ap.isActive(E))
      assert(!ap.isAllActive)
      assert(!ap.isAllPassive)
      assert(ap.allActive == Set(C, E))
      assert(ap.allPassive == Set(A, B, D))
      ap.activateAll()
      assert(ap.allActive == elements)
      assert(ap.allPassive.isEmpty)
      assert(ap.isAllActive)
      ap.deactivateAll()
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(ap.isAllPassive)
   }

   test("immutable") {
      var ap = immutable.ActivePassive(elements)
      for e <- elements do
         assert(!ap.isActive(e))
         assert(ap.isPassive(e))
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(!ap.isAllActive)
      assert(ap.isAllPassive)

      assertThrows[IllegalArgumentException](ap.activate(X))
      assertThrows[IllegalArgumentException](ap.deactivate(X))

      ap = ap.activate(D)
      ap = ap.activate(C)
      ap = ap.deactivate(D)
      ap = ap.activate(E)
      ap.activate(A)
      assert(ap.isPassive(A))
      assert(ap.isPassive(B))
      assert(ap.isActive(C))
      assert(ap.isPassive(D))
      assert(ap.isActive(E))
      assert(!ap.isAllActive)
      assert(!ap.isAllPassive)
      assert(ap.allActive == Set(C, E))
      assert(ap.allPassive == Set(A, B, D))
      ap = ap.activateAll()
      assert(ap.allActive == elements)
      assert(ap.allPassive.isEmpty)
      assert(ap.isAllActive)
      ap = ap.deactivateAll()
      assert(ap.allActive.isEmpty)
      assert(ap.allPassive == elements)
      assert(ap.isAllPassive)
   }

   test("functional") {
      import functional.*

      var ap = createActivePassive(elements)
      for e <- elements do
         assert(!isActive(ap, e))
         assert(isPassive(ap, e))
      assert(allActive(ap).isEmpty)
      assert(allPassive(ap) == elements)
      assert(!isAllActive(ap))
      assert(isAllPassive(ap))

      assertThrows[IllegalArgumentException](activate(ap, X))
      assertThrows[IllegalArgumentException](deactivate(ap, X))

      ap = activate(ap, D)
      ap = activate(ap, C)
      ap = deactivate(ap, D)
      ap = activate(ap, E)
      activate(ap, A)
      assert(isPassive(ap, A))
      assert(isPassive(ap, B))
      assert(isActive(ap, C))
      assert(isPassive(ap, D))
      assert(isActive(ap, E))
      assert(!isAllActive(ap))
      assert(!isAllPassive(ap))
      assert(allActive(ap) == Set(C, E))
      assert(allPassive(ap) == Set(A, B, D))
      ap = activateAll(ap)
      assert(allActive(ap) == elements)
      assert(allPassive(ap).isEmpty)
      assert(isAllActive(ap))
      ap = deactivateAll(ap)
      assert(allActive(ap).isEmpty)
      assert(allPassive(ap) == elements)
      assert(isAllPassive(ap))
   }
