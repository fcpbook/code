package chap06

import chap06.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*
import tinyscalautils.text.printout

class Section_2_Tests extends AnyFunSuite:
   test("factorial") {
      assert(factorial(0) == 1)
      assert(factorial(10) == 3628800)
   }

   test("hanoi") {
      assert(printout(hanoi(0, L, M, R)).isEmpty)
      assert(
        printout(hanoi(3, L, M, R)) ==
           """L -> R
             |L -> M
             |R -> M
             |L -> R
             |M -> L
             |M -> R
             |L -> R
             |""".stripMargin
      )
   }
