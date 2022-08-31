package chap08.avl.bst.variant

import chap08.avl.bst
import chap08.avl.bst.BinTree

import scala.language.adhocExtensions

class Node(key: Int, left: BinTree, right: BinTree)
    extends bst.Node(key: Int, left: BinTree, right: BinTree):
   override def - (k: Int): BinTree =
      if k < key then Node(key, left - k, right).avl
      else if k > key then Node(key, left, right - k).avl
      else if left.isEmpty then right
      else if right.isEmpty then left
      else if left.height > right.height then
         val (maxLeft, othersLeft) = left.maxRemoved
         Node(maxLeft, othersLeft, right) // no call to avl needed
      else
         val (minRight, othersRight) = right.minRemoved
         Node(minRight, left, othersRight) // no call to avl needed
