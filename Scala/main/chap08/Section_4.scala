package chap08

import chap08.bst.BinTree
import chap08.bst.BinTree.empty

object Section_4:
   // DON'T DO THIS
   def fromSet(keys: Set[Int]): BinTree =
      var set = empty
      for key <- keys do set += key
      set
