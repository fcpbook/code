package chap28

import chap28.Section_1.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.timing.timeIt

class Section_1_Tests extends AnyFunSuite with Tolerance:
   test("safe") {
      val f: String => Option[Int] = safe(str => if str.isEmpty then None else Some(str.toInt))
      assert(f("42").contains(42))
      assert(f("").isEmpty)
      assert(f("forty-two").isEmpty)
   }

   test("success") {
      val runner         = Runner(Seq(S1, S2, S3))
      val (result, time) = timeIt(runner.compute('A'))
      assert(result.contains('A'))
      assert(time === 4.0 +- 0.1)
   }

   test("failure") {
      val runner         = Runner(Seq(S1, S2, S3))
      val (result, time) = timeIt(runner.compute('B'))
      assert(result.isEmpty)
      assert(time === 2.0 +- 0.1)
   }
