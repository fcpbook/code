package chap15

import chap15.Section_5.*
import mocks.Chap15.{ book1, book3, solutionIsFound, value }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in

import scala.collection.immutable.{ ArraySeq, Iterable, Set }
import scala.language.reflectiveCalls
import scala.reflect.Selectable.reflectiveSelectable

class Section_5_Tests extends AnyFunSuite:
   test("LUB") {
      assertCompiles("demo1 = List(0)")
      assertTypeError("demo1 = List(None)")
      assertTypeError("demo1 = ArraySeq(0)")
      assertCompiles("demo2 = ArraySeq(0)")
      assertTypeError("demo2 = List(None)")
      assertCompiles("demo3 = None")
      assertCompiles("demo4 = List(Some(0))")
      assertTypeError("demo4 = List(None)")
      assertCompiles("demo5 = List(None)")
      assertTypeError("demo5 = List(Some(None))")
      assertCompiles("demo6 = 42")
      assertCompiles("demo7 = List(book3)")
      assertTypeError("demo7 = List(None)")
      assertCompiles("demo8 = List(None)")
   }

   test("complex type") {
      class A extends Iterable[Int]:
         def iterator: Iterator[Int] = Iterator.single(1)

      class B extends A with (Int => Int | Boolean):
         def apply(x: Int): Int | Boolean = if x == 1 then x else false

      class C extends B with Equals:
         def canEqual(any: Any) = false

      assertTypeError("demo9 = A()")
      assertTypeError("demo9 = B()")
      assertCompiles("demo9 = C()")

      assert(demo10() in Set(1, 4))
      assert(demo11() == false || demo11() == 2)
      assert(demo12())
   }

   test("Java type") {
      assert(Section_5J.demo() == 42)
   }

   test("type broadening declarations") {
      assertTypeError("""var books = List.empty
                        |books ::= book1
                        |""".stripMargin)
      assert(Demo13.books == List(book1))
      assert(Demo14.books == List(book1))
      assert(Demo15.books == List(book1))

      assertTypeError("""var solution = None
                        |if solutionIsFound then solution = Some(value)
                        |""".stripMargin)
      assert(Demo16.solution.forall(_ == value))
   }

   test("structural type") {
      task.run()
      assert(task.result == 42)
   }

   test("implicit conversion") {
      assert(chars == "a string".toSeq)
   }
