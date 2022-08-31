package chap28

import chap28.Section_3.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.*
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.timeIt

class Section_3_Tests extends AnyFunSuite with Tolerance:
   test("success") {
      val runner         = Runner(Seq(S1, S2, S3), global)
      val (result, time) = timeIt(runner.compute('A', 2.5))
      assert(result.contains('A'))
      assert(time === 2.0 +- 0.1)
   }

   test("failure") {
      val runner         = Runner(Seq(S1, S2, S3), global)
      val (result, time) = timeIt(runner.compute('B', 10.0))
      assert(result.isEmpty)
      assert(time === 2.0 +- 0.1)
   }

   test("timeout 1") {
      val runner         = Runner(Seq(S1, S2, S3), global)
      val (result, time) = timeIt(runner.compute('A', 1.0))
      assert(result.isEmpty)
      assert(time === 1.0 +- 0.1)
   }

   test("timeout 2") {
      val exec           = Executors.newThreadPool(availableProcessors)
      val runner         = Runner(Seq.fill(100)(S3), exec)
      val (result, time) = timeIt(runner.compute('B', 1.0))
      assert(result.isEmpty)
      assert(time === 1.0 +- 0.1)
      assert(exec.shutdownAndWait(0.1))
   }
