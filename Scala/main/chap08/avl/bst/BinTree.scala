package chap08.avl.bst

import java.util.NoSuchElementException

sealed abstract class BinTree:
   def isEmpty: Boolean
   def contains(k: Int): Boolean

   def size: Int
   def height: Int

   def min: Int
   def max: Int

   def + (k: Int): BinTree
   def - (k: Int): BinTree

   def toList: List[Int] = makeList(List.empty)

   private[bst] def makeList(list: List[Int]): List[Int]

   private[bst] def imbalance: Int
   private[bst] def rotateRight: Node
   private[bst] def rotateLeft: Node

   private[bst] def minRemoved: (Int, BinTree)
   private[bst] def maxRemoved: (Int, BinTree)
end BinTree

private case object Empty extends BinTree:
   def isEmpty          = true
   def contains(k: Int) = false

   def size   = 0
   def height = 0

   def min = throw NoSuchElementException("Empty.min")
   def max = throw NoSuchElementException("Empty.max")

   def + (k: Int): BinTree = Node(k, Empty, Empty)
   def - (k: Int): BinTree = this

   def makeList(list: List[Int]) = list

   def imbalance = 0

   def rotateRight = throw AssertionError("empty trees are never rotated")
   def rotateLeft  = throw AssertionError("empty trees are never rotated")
   def minRemoved  = throw AssertionError("empty trees are never reduced")
   def maxRemoved  = throw AssertionError("empty trees are never reduced")
end Empty

private case class Node(key: Int, left: BinTree, right: BinTree) extends BinTree:
   def isEmpty = false

   def contains(k: Int) =
      if k < key then left.contains(k)
      else if k > key then right.contains(k)
      else true

   def size   = 1 + left.size + right.size
   val height = 1 + (left.height max right.height) // use val to improve avl performance

   def min = if left.isEmpty then key else left.min
   def max = if right.isEmpty then key else right.max

   def + (k: Int): Node =
      if k < key then Node(key, left + k, right).avl
      else if k > key then Node(key, left, right + k).avl
      else this

   def minRemoved: (Int, BinTree) =
      if left.isEmpty then (key, right)
      else
         val (min, othersLeft) = left.minRemoved
         (min, Node(key, othersLeft, right).avl)

   def maxRemoved: (Int, BinTree) =
      if right.isEmpty then (key, left)
      else
         val (max, othersRight) = right.maxRemoved
         (max, Node(key, left, othersRight).avl)

   def - (k: Int): BinTree =
      if k < key then Node(key, left - k, right).avl
      else if k > key then Node(key, left, right - k).avl
      else if left.isEmpty then right
      else if right.isEmpty then left
      else
         val (minRight, othersRight) = right.minRemoved
         Node(minRight, left, othersRight).avl

   def makeList(list: List[Int]) = left.makeList(key :: right.makeList(list))

   def imbalance = right.height - left.height

   def rotateRight: Node = (left: @unchecked) match
      case Node(keyL, leftL, rightL) => Node(keyL, leftL, Node(key, rightL, right))

   def rotateLeft: Node = (right: @unchecked) match
      case Node(keyR, leftR, rightR) => Node(keyR, Node(key, left, leftR), rightR)

   def avl: Node = imbalance match
      case -2 => left.imbalance match
            case 0 | -1 => rotateRight
            case 1      => Node(key, left.rotateLeft, right).rotateRight
      case 2 => right.imbalance match
            case 0 | 1 => rotateLeft
            case -1    => Node(key, left, right.rotateRight).rotateLeft
      case 0 | 1 | -1 => this
end Node

object BinTree:
   def empty: BinTree = Empty

   def fromSet(keys: Set[Int]): BinTree =
      val keySeq = keys.toIndexedSeq.sorted

      def makeTree(from: Int, to: Int): BinTree =
         if from > to then Empty
         else
            val mid = (from + to) / 2
            Node(keySeq(mid), makeTree(from, mid - 1), makeTree(mid + 1, to))

      makeTree(0, keySeq.length - 1)
   end fromSet
end BinTree
