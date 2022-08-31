package mocks

import chap02.Section_4.Node

object Chap02:
   case class Edge(from: Node, to: Node)

   var num = 0

   case class Writer(name: String, autoflush: Boolean)
