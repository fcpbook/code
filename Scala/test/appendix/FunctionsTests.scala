package appendix

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

import java.util as ju

class FunctionsTests extends AnyFunSuite:
   import FunctionsJ.*

   test("firstLast") {
      assert(firstLast(ju.List.of(A, B, C)) == ju.List.of(A, C))
   }

   test("average") {
      assert(demo1 == (1.0 + 2.3 + 4.1) / 3.0)
      assert(demo2 == 15.0)
      assert(average(10.0) == 10.0)
   }
