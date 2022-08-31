package chap08

import chap08.Section_4.*
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.BitSet

class Section_4_Tests extends AnyFunSuite:
   test("fromSet, bad") {
      val tree = fromSet(BitSet.fromSpecific(1 to 15))
      assert(tree.toList == (1 to 15))
      assert(tree.height == 15)
   }
