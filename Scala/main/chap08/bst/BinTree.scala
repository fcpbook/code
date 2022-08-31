package chap08.bst

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
   private[bst] def minRemoved: (Int, BinTree)
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

   def minRemoved: (Int, BinTree) =
      throw AssertionError("empty trees are never reduced")
end Empty

private final case class Node(key: Int, left: BinTree, right: BinTree) extends BinTree:
   def isEmpty = false

   def contains(k: Int) =
      if k < key then left.contains(k)
      else if k > key then right.contains(k)
      else true

   def size   = 1 + left.size + right.size
   def height = 1 + (left.height max right.height)

   def min = if left.isEmpty then key else left.min
   def max = if right.isEmpty then key else right.max

   def + (k: Int): Node =
      if k < key then Node(key, left + k, right)
      else if k > key then Node(key, left, right + k)
      else this

   def minRemoved: (Int, BinTree) =
      if left.isEmpty then (key, right)
      else
         val (min, othersLeft) = left.minRemoved
         (min, Node(key, othersLeft, right))

   def - (k: Int): BinTree =
      if k < key then Node(key, left - k, right)
      else if k > key then Node(key, left, right - k)
      else if left.isEmpty then right
      else if right.isEmpty then left
      else
         val (minRight, othersRight) = right.minRemoved
         Node(minRight, left, othersRight)

   def makeList(list: List[Int]) = left.makeList(key :: right.makeList(list))
end Node

object BinTree:
   def empty: BinTree = Empty

   def fromSet(keys: Set[Int]): BinTree =
      def makeTree(list: List[Int]): BinTree =
         if list.isEmpty then Empty
         else
            val (left, mid :: right) = list.splitAt(list.length / 2): @unchecked
            Node(mid, makeTree(left), makeTree(right))

      makeTree(keys.toList.sorted)
   end fromSet
end BinTree
