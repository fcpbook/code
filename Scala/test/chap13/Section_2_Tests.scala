package chap13

import chap13.Section_1.words
import chap13.Section_2.*
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite:
   test("between") {
      assert(between(words, "two", "four") == List("two", "three", "four"))
      assert(between(words, "four", "two") == List("two", "three", "four"))
      assert(between(words, "two", "five").isEmpty)
      assert(between(words, "ten", "four").isEmpty)
   }
