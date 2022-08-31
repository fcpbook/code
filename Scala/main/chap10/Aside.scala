package chap10

import mocks.Chap10.struct

object Aside:
   object Functor:
      import mocks.Chap10.Functor.*

      def demo1: Boolean = struct == struct.map(x => x)

      def demo2: Boolean = struct == struct.map(identity)

      def demo3: Boolean = struct.map(f).map(g) == struct.map(x => g(f(x)))

      def demo4: Boolean = struct.map(f).map(g) == struct.map(f andThen g)
   end Functor

   object Monad:
      import mocks.Chap10.Monad.*

      def demo1: Boolean = struct.flatMap(unit) == struct

      def demo2: Boolean = unit(x).flatMap(f) == f(x)

      def demo3: Boolean = struct.flatMap(f).flatMap(g) == struct.flatMap(x => f(x).flatMap(g))

      def map[A, B](struct: List[A])(f: A => B): List[B] = struct.flatMap(x => unit(f(x)))
