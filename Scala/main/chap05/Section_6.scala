package chap05

import tinyscalautils.assertions.require

object Section_6:
   opaque type Zipper[A] = (List[A], List[A])

   def fromList[A](list: List[A]): Zipper[A] =
      require(list.nonEmpty, "no empty zipper")
      (Nil, list)

   def toList[A](zipper: Zipper[A]): List[A] =
      zipper match
         case (left, right) => left.reverse ::: right

   def get[A](zipper: Zipper[A]): A =
      (zipper: @unchecked) match
         case (_, x :: _) => x

   def set[A](zipper: Zipper[A], value: A): Zipper[A] = zipper match
      case (left, _ :: right) => (left, value :: right)
      case _                  => throw new AssertionError("empty zipper")

   def moveLeft[A](zipper: Zipper[A]): Zipper[A] =
      zipper match
         case (x :: left, right) => (left, x :: right)
         case _                  => zipper

   def moveRight[A](zipper: Zipper[A]): Zipper[A] =
      zipper match
         case (left, x :: right) if right.nonEmpty => (x :: left, right)
         case _                                    => zipper

   object NoGuardVariant:
      def moveRight[A](zipper: Zipper[A]): Zipper[A] = zipper match
         case (left, x :: (right @ _ :: _)) => (x :: left, right)
         case _                             => zipper
