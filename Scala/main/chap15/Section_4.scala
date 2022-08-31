package chap15

object Section_4:
   object Polymorphic:
      abstract class ListADT:
         type List[_]
         def empty[A]: List[A]
         def cons[A](x: A, list: List[A]): List[A]
         def head[A](list: List[A]): A
         def tail[A](list: List[A]): List[A]
         def length[A](list: List[A]): Int

   object Monomorphic:
      abstract class ListADT[A]:
         type List
         val empty: List
         def cons(x: A, list: List): List
         def head(list: List): A
         def tail(list: List): List
         def length(list: List): Int
