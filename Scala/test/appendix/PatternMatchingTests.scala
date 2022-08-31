package appendix

import org.scalatest.funsuite.AnyFunSuite

class PatternMatchingTests extends AnyFunSuite:
   import PatternMatchingJ.*

   test("verbosity") {
      assert(parseVerbosity("") == 0)
      assert(parseVerbosity("-v") == 1)
      assert(parseVerbosity("-vv") == 2)
   }
