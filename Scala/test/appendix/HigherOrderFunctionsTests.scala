package appendix

import org.scalatest.funsuite.AnyFunSuite

import java.util as ju

class HigherOrderFunctionsTests extends AnyFunSuite:
   import HigherOrderFunctions.*
   import HigherOrderFunctionsJ.*

   test("byLength (Scala)") {
      assert(HigherOrderFunctions.byLength.compare("foo", "bar") == 0)
      assert(HigherOrderFunctions.byLength.compare("A", "AAA") < 0)
      assert(HigherOrderFunctions.byLength.compare("AAA", "A") > 0)
   }

   test("byLength (Java)") {
      assert(HigherOrderFunctionsJ.byLength.compare("foo", "bar") == 0)
      assert(HigherOrderFunctionsJ.byLength.compare("A", "AAA") < 0)
      assert(HigherOrderFunctionsJ.byLength.compare("AAA", "A") > 0)
   }

   test("len") {
      assert(Len1.len.asInstanceOf[String => Int]("foo") == 3)
      assert(Len2.len("foo") == 3)
      assert(len.applyAsInt("foo") == 3)
   }

   test("existsOrEmpty") {
      assert(e)
      assert(existsOrEmpty(List.empty)(_ => false))
   }

   test("negate") {
      assert(Neg1.neg.test(-1))
      assert(!Neg1.neg.test(1))
      assert(Neg2.neg.test(-1))
      assert(!Neg2.neg.test(1))
   }

   test("map") {
      assert(ListMap.celsius == ju.List.of(31, 33, 26, 21, 38, 37, 21))
      assert(ArrayMap.celsius.sameElements(Array(31, 33, 26, 21, 38, 37, 21)))
   }

   test("flatMap") {
      assert(celsius == ju.List.of(6, 0, 22, 38))
   }

   test("memo") {
      var calls = 0
      def len(str: String): Int =
         calls += 1
         str.length
      val f = memo(str => len(str))
      assert(f("foo") == 3)
      assert(calls == 1)
      assert(f("foo") == 3)
      assert(calls == 1)
      assert(f("bar") == 3)
      assert(calls == 2)
      assert(f("foo") == 3)
      assert(f("bar") == 3)
      assert(calls == 2)
   }

   test("single") {
      val f = single((x: Int) => x + 1)
      assert(f(0) == 1)
      assertThrows[IllegalStateException](f(1))
   }
