package chap10

object Section_8:
   sealed abstract class BinTree:
      def exists(test: Int => Boolean): Boolean
      def foreach[U](f: Int => U): Unit
      def fold[A](init: A)(f: (A, Int) => A): A

   case object Empty extends BinTree:
      def exists(test: Int => Boolean): Boolean = false
      def foreach[U](f: Int => U): Unit         = ()
      def fold[A](init: A)(f: (A, Int) => A): A = init

   case class Node(key: Int, left: BinTree, right: BinTree) extends BinTree:
      def exists(test: Int => Boolean): Boolean =
         test(key) || left.exists(test) || right.exists(test)

      def foreach[U](f: Int => U): Unit =
         left.foreach(f)
         f(key)
         right.foreach(f)

      def fold[A](init: A)(f: (A, Int) => A): A = right.fold(f(left.fold(init)(f), key))(f)
   end Node
