package chap12

import chap12.Section_6.*
import org.scalatest.funsuite.AnyFunSuite

import scala.jdk.CollectionConverters.{ ListHasAsScala, SeqHasAsJava }

class Section_6_Tests extends AnyFunSuite:
   private val lorem = List(
     "Lorem ipsum dolor sit amet, consectetur adipiscing",
     "elit, sed do eiusmod tempor incididunt ut labore",
     "et dolore magna aliqua."
   )

   private val longLorem = List("consectetur", "adipiscing", "incididunt")

   test("longWords") {
      assert(WithLists.longWords(lorem, 10) == longLorem)
      assert(WithFold.longWords(lorem, 10) == longLorem)
      assert(longWords(lorem, 10) == longLorem)
      assert(Section_6J.longWords(lorem.asJava, 10).asScala == longLorem)
   }
