package chap19

import chap19.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.text.StringLetters.X
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.availableProcessors

class Section_2_Tests extends AnyFunSuite:
   test("demo") {
      for n <- 1 to 2 * availableProcessors do
         val m    = 100_000 / n
         val demo = Demo()
         syncForkJoin(1 to n)(_ => demo.addStrings(m, X))
         assert(demo.shared.size == n * m)
   }
