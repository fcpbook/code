package chap10

import chap10.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.util.FastRandom

class Section_4_Tests extends AnyFunSuite:
   private val numbers = 0 :: List.fill(99)(FastRandom.between(-50, 51))

   test("demos") {
      assert(demo1 == "f(f(f(X,A),B),C)")
      assert(demo2 == "f(A,f(B,f(C,X)))")
      assert(Reduce.demo == "f(f(A,B),C)")
      val lines = numbers.map(_.toString)
      assert(demo3(lines) == demo4(lines))
   }

   test("calculate") {
      val v1 = Iter.calculate(numbers)
      val v2 = Rec.calculate(numbers)
      val v3 = FoldLeft.calculate(numbers)
      assert(Set(v1, v2, v3).size == 1)
   }

   test("sum, product, reverse, filter") {
      assert(sum(numbers) == numbers.sum)
      assert(Reduce.sum(numbers) == numbers.sum)
      assert(product(numbers) == numbers.product)
      assert(Reduce.product(numbers) == numbers.product)
      assert(reverse(numbers) == numbers.reverse)
      assert(filter(numbers, _ < 50) == numbers.filter(_ < 50))
   }
