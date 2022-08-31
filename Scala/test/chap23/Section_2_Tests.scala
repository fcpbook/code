package chap23

import chap23.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("demo") {
      for n <- 1 to 64 do
         demo1(n)
         demo2(n)
   }
