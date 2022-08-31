package chap05

object Section_3:
   val someNum: Option[Int] = Some(42)
   val noNum: Option[Int]   = None

   def demo1(optNum: Option[Int]): Int =
      optNum match
         case Some(x) => if x > 0 then x else 0
         case None    => 0

   def demo2(optNum: Option[Int]): Int =
      optNum match
         case Some(x) if x > 0 => x
         case _                => 0

   def demo3(optNum: Option[Int]): Int =
      optNum match
         case None    => 0
         case Some(x) => if x > 0 then x else 0

   def demo4(optNum: Option[Int]): Int =
      // DON'T DO THIS!
      optNum: @unchecked match
         case _                => 0
         case Some(x) if x > 0 => x

