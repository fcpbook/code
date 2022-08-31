package chap02

import mocks.Chap02.Edge

object Section_4:
   class Node:
      def --> (that: Node): Edge = Edge(this, that)

      infix def to(that: Node): Edge = Edge(this, that)
   end Node
