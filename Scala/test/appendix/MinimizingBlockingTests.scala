package appendix

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.shutdownAndWait

class MinimizingBlockingTests extends AnyFunSuite:
   import MinimizingBlockingJ.*

   test("termination/deadlock") {
      for n <- 1 to 10; m <- 1 to 10 do assert(demo(n, m).shutdownAndWait(0.5) == m <= n)
   }
