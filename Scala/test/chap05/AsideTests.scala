package chap05

import chap05.Aside.*
import org.scalatest.funsuite.AnyFunSuite

class AsideTests extends AnyFunSuite:
   val stooges: Set[Stooge] = Set("Larry", "Curly", "Moe")
   val digits: Set[Digit]   = Set(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

   def stoogeAndDigit(x: StoogeAndDigit) =
      assert(stooges.contains(x(0): Stooge))
      assert(digits.contains(x(1): Digit))

   test("StoogeAndDigit") {
      val all: Set[StoogeAndDigit] = for x <- stooges; y <- digits yield (x, y)
      assert(all.size == 30)
   }

   test("StoogeLink") {
      val all: Set[StoogeLink] =
         for x <- stooges; y <- stooges; z <- Set(true, false) yield (x, y, z)
      assert(all.size == 18)
   }

   test("StoogeOrDigit") {
      import StoogeOrDigit.*
      val all: Set[StoogeOrDigit] =
         for x <- stooges; y <- digits; z <- Set(S(x), D(y)) yield z
      assert(all.size == 13)
   }

   test("Number") {
      import Number.*
      val all: Set[Number] =
         for x <- digits; y <- Set(Zero, Pos(x), Neg(x)) yield y
      assert(all.size == 21)
   }

   test("OptionalStooge") {
      val all: Set[OptionalStooge] =
         for x <- stooges; y <- Set(None, Some(x)) yield y
      assert(all.size == 4)
   }

   test("OptionalStoogeAndDigit") {
      val all: Set[OptionalStoogeAndDigit] =
         for x <- stooges; y <- Set(None, Some(x)); z <- digits yield (y, z)
      assert(all.size == 40)
   }

   test("StoogeAndDigitOrDigit") {
      import StoogeAndDigitOrDigit.*
      val all: Set[StoogeAndDigitOrDigit] =
         for x <- stooges; y <- digits; z <- Set(SD(x, y), D(y)) yield z
      assert(all.size == 40)
   }

   test("IntList") {
      def toList(l: IntList): List[Int] = l match
         case IntList.Nil        => Nil
         case IntList.Cons(h, t) => h :: toList(t)
      def fromList(l: List[Int]): IntList = l match
         case Nil    => IntList.Nil
         case h :: t => IntList.Cons(h, fromList(t))
      val list = List(1, 2, 3)
      assert(toList(fromList(list)) == list)
   }
