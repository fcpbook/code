package chap03

import chap03.Section_4.*
import chap03.Section_4J.*
import org.scalatest.funsuite.AnyFunSuite

abstract class VerbosityTests extends AnyFunSuite:
   protected def testVerbosity(parse: String => Int) =
      assert(Seq("-v", "-vv", "-vvv").map(parse) == Seq(1, 2, 0))

class Section_4_Tests extends VerbosityTests:
   test("WithSwitch") {
      assert(WithSwitch().verbosity == 2)
   }

   test("WithVar") {
      testVerbosity(str => Demo1(str).verbosity)
   }

   test("WithVal") {
      testVerbosity(str => Demo2(str).verbosity)
   }
