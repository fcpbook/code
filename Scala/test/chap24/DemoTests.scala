package chap24

import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.PrintingMode
import tinyscalautils.timing.timeIt
import util.invokeAndWait

import java.util.concurrent.ForkJoinPool

class DemoTests extends AnyFunSuite with Tolerance:
   def checkCommon(expectedTime: Double, prefix: String, code: PrintingMode ?=> Unit) =
      val (lines, time) = timeIt(invokeAndWait(code, waitForChildren = false))
      assert(time === expectedTime +- 0.1)
      assert(lines.length == 8)
      assert(lines(0) == "main: START")
      assert(lines(7) == "main: END")
      for i <- 2 to 6 do assert(lines(i).startsWith(prefix))
      lines

   def checkSequential(prefix: String, code: PrintingMode ?=> Unit) =
      val lines = checkCommon(6.0, prefix, code)
      assert(lines(1).endsWith(": begin 2"))
      assert(lines(2).endsWith(": end 2"))
      assert(lines(3).endsWith(": begin 1"))
      assert(lines(4).endsWith(": end 1"))
      assert(lines(5).endsWith(": begin 3"))
      assert(lines(6).endsWith(": end 3"))

   def checkParallel(prefix: String, code: PrintingMode ?=> Unit) =
      val lines = checkCommon(3.0, prefix, code)
      assert(lines.exists(line => line.endsWith(": begin 1")))
      assert(lines.exists(line => line.endsWith(": begin 2")))
      assert(lines.exists(line => line.endsWith(": begin 3")))
      assert(lines.exists(line => line.endsWith(": end 1")))
      assert(lines.exists(line => line.endsWith(": end 2")))
      assert(lines.exists(line => line.endsWith(": end 3")))

   def checkBounded(prefix: String, code: PrintingMode ?=> Unit) =
      val lines = checkCommon(4.0, prefix, code)
      assert(lines(3).endsWith(": end 1"))
      assert(lines(4).endsWith(": begin 3"))
      assert(lines(5).endsWith(": end 2"))
      assert(lines(6).endsWith(": end 3"))
      assert(lines.exists(line => line.endsWith(": begin 1")))
      assert(lines.exists(line => line.endsWith(": begin 2")))

   test("sequential, demo") {
      checkSequential("main", Section_1.demo())
   }

   test("new thread, demo1") {
      checkParallel("Thread-", Section_2.demo1())
   }

   test("new thread, demo2") {
      checkSequential("Thread-", Section_2.demo2())
   }

   test("bounded, demo") {
      checkBounded("Thread-", Section_3.demo())
   }

   test("local pool, demo") {
      checkBounded("pool-", Section_4.demo())
   }

   test("shared pool, demo") {
      checkBounded("pool-", Section_5.demo())
   }

   test("bounded pool, demo") {
      checkBounded("pool-", Section_6.demo())
   }

   test("parallel, demo") {
      assume {
         scala.concurrent.ExecutionContext.global match
            case fj: ForkJoinPool => fj.getParallelism >= 3
            case _                => false
      }
      checkParallel("scala-execution-context-global-", Section_7.demo())
   }
