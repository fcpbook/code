package appendix

object RecursiveProgramming {
  fun factorial(n: Int): Int {
    tailrec fun loop(m: Int, f: Int): Int = if (m == 0) f else loop(m - 1, f * m)
    return loop(n, 1)
  }

  sealed interface BinTree
  object Empty : BinTree
  data class Node(val key: Int, val left: BinTree, val right: BinTree) : BinTree

  fun size(tree: BinTree): Int = when (tree) {
    is Empty -> 0
    is Node -> 1 + size(tree.left) + size(tree.right)
  }
}
