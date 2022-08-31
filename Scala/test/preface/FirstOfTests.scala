package preface

import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.{ delay, timeIt }

class FirstOfTests extends AnyFunSuite, Tolerance:

   private val fast: StringComputation = () => delay(1.0)("fast")
   private val slow: StringComputation = () => delay(1.1)("slow")

   test("FirstOf") {
      import FirstOf.firstOf

      /* The way firstOf is written, the executor cannot be shut down.
       * So, using 'global here. */

      val (str, time) = timeIt(firstOf(slow, fast))
      assert(str == "fast")
      assert(time === 1.0 +- 0.1)
   }

   test("FirstOf1") {
      import FirstOf1.firstOf

      val (str, time) = timeIt(firstOf(slow, fast))
      assert(str == "fast")
      assert(time === 1.0 +- 0.1)
   }

   test("FirstOf2") {
      import FirstOf2.firstOf

      val (str, time) = timeIt(firstOf(slow, fast, global))
      assert(str == "fast")
      assert(time === 1.0 +- 0.1)
   }
