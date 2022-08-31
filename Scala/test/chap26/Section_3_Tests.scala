package chap26

import chap26.Section_3.*
import mocks.Chap26.written
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.withThreadPoolAndWait

import scala.concurrent.Future

class Section_3_Tests extends AnyFunSuite:
   test("multiplyAndWrite") {
      written.clear()
      multiplyAndWrite(Future("A"), 3)
      assert(written.take() == "AAA")
      multiplyAndWrite(Future(throw Exception("X")), 0)
      assert(written.take() == "exception: X")
   }

   for (map, name) <- Seq(map -> "map", MapVariant.map -> "map, variant") do
      test(name) {
         val ex = Exception()
         withThreadPoolAndWait(global) {
            for
               str <- map(Future(42), _.toString)
               e   <- map(Future(throw ex), identity).failed
            yield
               assert(str == "42")
               assert(e == ex)
         }
      }

   for (multiply, name) <-
         Seq(multiply -> "multiply", MultiplyVariant.multiply -> "multiply, variant")
   do
      test(name) {
         withThreadPoolAndWait(global) {
            val ex = Exception()
            for
               str <- multiply(Future("A"), 3)
               e   <- multiply(Future(throw ex), 0).failed
            yield
               assert(str == "AAA")
               assert(e == ex)
         }
      }
