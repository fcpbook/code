package chap05

import chap05.Section_6.*
import org.scalatest.funsuite.AnyFunSuite

class Section_6_Tests extends AnyFunSuite:
   test("zipper") {
      val plato = "Plato".toList
      var z     = fromList(plato)
      assert(toList(z) == plato)
      assert(get(z) == 'P')
      z = set(z, 'p')
      assert(get(z) == 'p')
      z = moveRight(z)
      assert(get(z) == 'l')
      z = set(z, 'L')
      assert(get(z) == 'L')
      z = moveRight(z)
      assert(get(z) == 'a')
      z = moveRight(moveRight(z))
      assert(get(z) == 'o')
      z = moveRight(NoGuardVariant.moveRight(z))
      assert(get(z) == 'o')
      z = moveLeft(moveLeft(moveLeft(z)))
      assert(get(z) == 'L')
      z = moveLeft(moveLeft(moveLeft(z)))
      assert(get(z) == 'p')
   }
