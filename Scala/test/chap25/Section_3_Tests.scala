package chap25

import chap25.Section_3.*
import mocks.Chap25.{ Request, failedAd, someAd, timeoutAd }
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.timing.timeIt

class Section_3_Tests extends AnyFunSuite with Tolerance:
   test("demo 1") {
      val (ad, time) = timeIt(demo(Request(1)))
      assert(time === 0.5 +- 0.1)
      assert(ad eq timeoutAd)
   }

   test("demo 2") {
      val (ad, time) = timeIt(demo(Request(0)))
      assert(time === 0.0 +- 0.1)
      assert(ad eq someAd)
   }

   test("demo 3") {
      val (ad, time) = timeIt(demo(Request(-1)))
      assert(time === 0.0 +- 0.1)
      assert(ad eq failedAd)
   }
