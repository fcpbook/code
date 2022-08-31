package chap08

object Section_2:
   enum BinTree:
      case Empty
      case Node(key: Int, left: BinTree, right: BinTree)

   import BinTree.*

   def isEmpty(tree: BinTree): Boolean = tree match
      case Empty         => true
      case Node(_, _, _) => false
