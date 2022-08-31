package chap09

import chap09.Aside.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class AsideTests extends AnyFunSuite:
   test("Demo1") {
      import Demo1.*
      str = "foo"
      for x <- -10 to 0 do assert(f(x) == 3)
      for x <- 1 to 10 do assert(f(x) == x)
      assert(str == "foo")
   }

   test("demo2") {
      assert(printout(demo2()) == "2\n1\n")
   }

   test("demo3") {
      assert(printout(demo3()) == "2\n2\n")
   }

   test("demo4") {
      assert(printout(demo4()) == "foo\n")
   }

   test("demo5") {
      assert(printout(demo5()) == "FOO\n")
   }

   test("out of scope 1") {
      assertDoesNotCompile {
         """var x = 1
           |
           |def f() =
           |   var x = 2
           |   if x > 0 then
           |      var x = 3
           |      var y = 4
           |   println(x) // prints 2
           |   println(y) // rejected at compile-time
           |
           |f()
           |println(x) // prints 1
           |""".stripMargin
      }
   }

   test("out of scope 2") {
      assertDoesNotCompile {
         """var x = 1
           |
           |def f() =
           |   var x = 2
           |   if x > 0 then
           |      var x = 3
           |      var y = 4
           |   println(x) // prints 2
           |
           |f()
           |println(x) // prints 1
           |println(y) // rejected at compile-time
           |""".stripMargin
      }
   }
