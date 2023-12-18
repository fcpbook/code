package chap05

import chap03.VerbosityTests
import chap05.Section_1.*

import java.io.IOException

class Section_1_Tests extends VerbosityTests:
   test("WithVal") {
      testVerbosity(str => Demo1(str).verbosity)
   }

   test("WithMatch") {
      testVerbosity(str => Demo2(str).verbosity)
   }

   test("parseOption") {
      assert(demo3("--") == "end of options")
      assert(demo3("--long") == "long option")
      assert(demo3("-short") == "short option")
      assert(demo3("plain") == "plain argument")
   }

   test("exceptions") {
      assert(demo4(throw IOException()) == "an I/O exception")
      assert(
        demo4(throw IllegalStateException()) == "can refer to the exception, e.g., e.getMessage"
      )
      assert(demo4(throw NullPointerException()) == "some other exception")
   }
