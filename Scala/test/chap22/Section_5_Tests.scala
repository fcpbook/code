package chap22

import chap22.Section_5.*
import mocks.Chap22.{ v0, v1, v2 }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in
import tinyscalautils.text.printout

class Section_5_Tests extends AnyFunSuite:
   test("demo") {
      val lines = printout {
         val demo = Demo()
         demo.t1.start(); demo.t2.start()
         demo.t1.join(); demo.t2.join(); demo.t3.join()
      }.linesIterator.toIndexedSeq
      assert(lines(0) == v1)
      assert(lines(1) in Set(v0, v1))
      assert(lines(2) == v2)
      assert(lines(3) in Set(v1, v2))
   }
