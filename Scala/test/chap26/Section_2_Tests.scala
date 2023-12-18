package chap26

import chap26.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.threads.withMarkedThreads
import tinyscalautils.text.PrintingMode
import util.invokeAndWait

import scala.concurrent.{ ExecutionContext, Future }

class Section_2_Tests extends AnyFunSuite:
   test("demo1") {
      withMarkedThreads() {
         val exec  = summon[ExecutionContext]
         val lines = invokeAndWait(demo1(exec), waitForChildren = true)
         assert(lines.length == 4)
         assert(lines(0) == "main: START")
         assert(lines(1) == "main: END")
         assert(lines(2).startsWith("MarkedThread-") && lines(2).endsWith(": Success(42)"))
         assert(
           lines(3).startsWith("MarkedThread-") &&
              lines(3).endsWith(": Failure(java.lang.NullPointerException)")
         )
         Future.unit
      }
   }
