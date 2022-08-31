package chap28

import chap28.Section_2.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.timing.timeIt

class Section_2_Tests extends AnyFunSuite with Tolerance:
   test("success") {
      val runner         = Runner(Seq(S1, S2, S3))
      val (result, time) = timeIt(runner.compute('A', 4.5))
      assert(result.contains('A'))
      assert(time === 4.0 +- 0.1)
   }

   test("failure") {
      val runner         = Runner(Seq(S1, S2, S3))
      val (result, time) = timeIt(runner.compute('B', 10.0))
      assert(result.isEmpty)
      assert(time === 2.0 +- 0.1)
   }

   test("timeout 1") {
      val runner         = Runner(Seq(S1, S2, S3))
      val (result, time) = timeIt(runner.compute('A', 3.0))
      assert(result.isEmpty)
      assert(time === 3.0 +- 0.1)
   }

   test("timeout 2") {
      val runner         = Runner(Seq.fill(100)(S3))
      val (result, time) = timeIt(runner.compute('B', 5.0))
      assert(result.isEmpty)
      assert(time === 5.0 +- 0.1)
   }
