package chap06

//noinspection NoTailRecursionAnnotation
object Aside:
   class NoTailRec:
      def zero(x: Int): 0 = if x == 0 then 0 else zero(x - 1)

   class TailRec:
      final def zero(x: Int): 0 = if x == 0 then 0 else zero(x - 1)
