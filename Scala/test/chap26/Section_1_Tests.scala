package chap26

import chap26.Section_1.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Await, Future, TimeoutException }

class Section_1_Tests extends AnyFunSuite:
   test("demo") {
      assert(demo1() == List(1, 1, 2, 6, 6, 8, 8, 8, 9))
   }

   test("OK") {
      val nums   = List(1, 6, 8, 6, 1, 8, 2, 8, 9)
      val sorted = nums.sorted
      val exec   = Executors.newThreadPool(3)
      100_000 times {
         assert(quickSort(nums, exec) == sorted)
      }
      exec.shutdown()
   }

   test("deadlock") {
      import tinyscalautils.threads.Executors.global

      val exec = Executors.newThreadPool(3)
      val nums = List(5, 4, 1, 3, 2)
      val f    = Future(quickSort(nums, exec))
      assertThrows[TimeoutException](Await.ready(f, 5.second))
      exec.shutdownNow()
   }
