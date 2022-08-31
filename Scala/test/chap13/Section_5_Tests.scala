package chap13

import chap13.Section_5.*
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.Path

class Section_5_Tests extends AnyFunSuite:
   test("no failure handling") {
      import NoFailureHandling.convert
      assert(convert(Path.of("temps")) == List("Austin: 38", "Big Spring: 33"))
      assertThrows[NumberFormatException](convert(Path.of("bad_file")))
   }

   test("with failure handling") {
      assert(convert(Path.of("temps")).get == List("Austin: 38", "Big Spring: 33"))
      assert(convert(Path.of("bad_file")).get == List("Austin: unknown", "[Big]"))
   }
