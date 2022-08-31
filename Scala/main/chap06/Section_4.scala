package chap06

object Section_4:
   enum BinTree[+A]:
      case Empty
      case Node(value: A, left: BinTree[A], right: BinTree[A])

   def size[A](tree: BinTree[A]): Int =
      import BinTree.{ Empty, Node }
      tree match
         case Empty                => 0
         case Node(_, left, right) => 1 + size(left) + size(right)

   enum Tree[+A]:
      case Empty
      case Node(value: A, trees: Forest[A])

   type Forest[A] = List[Tree.Node[A]]

   import Tree.{ Empty, Node }

   def treeSize[A](tree: Tree[A]): Int = tree match
      case Empty          => 0
      case Node(_, trees) => 1 + forestSize(trees)

   def forestSize[A](forest: Forest[A]): Int = forest match
      case Nil           => 0
      case tree :: trees => treeSize(tree) + forestSize(trees)
