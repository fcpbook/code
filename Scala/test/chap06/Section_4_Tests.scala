package chap06

import chap06.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_4_Tests extends AnyFunSuite:
   test("BinTree size") {
      import BinTree.{ Empty, Node }
      assert(size(Empty) == 0)
      assert(size(Node(A, Node(B, Empty, Empty), Node(C, Empty, Empty))) == 3)
   }

   test("size") {
      import Tree.{ Empty, Node }
      val abc: Node[String] = Node(A, List(Node(B, List.empty), Node(C, List.empty)))
      assert(treeSize(Empty) == 0)
      assert(treeSize(abc) == 3)
      assert(forestSize(List.empty) == 0)
      assert(forestSize(List(abc, abc)) == 6)
   }
