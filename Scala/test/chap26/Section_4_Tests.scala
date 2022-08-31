package chap26

import chap26.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ withThreadPoolAndWait, withThreads }
import tinyscalautils.util.FastRandom

import scala.concurrent.{ ExecutionContext, Future }

class Section_4_Tests extends AnyFunSuite:
   private def variant1(f1: Future[String], f2: Future[Int]) = Variant1.multiply(f1, f2).flatten

   for (multiply, name) <- Seq(
        multiply          -> "multiply",
        variant1          -> "multiply, variant 1",
        Variant2.multiply -> "multiply, variant 2",
        Variant3.multiply -> "multiply, variant 3",
        Variant4.multiply -> "multiply, variant 4",
      )
   do
      test(name) {
         withThreadPoolAndWait(global) {
            val ex = Exception()
            for
               str <- multiply(Future("A"), Future(3))
               e1  <- multiply(Future(throw ex), Future(0)).failed
               e2  <- multiply(Future(""), Future(throw ex)).failed
               e3  <- multiply(Future(throw ex), Future(throw ex)).failed
            yield
               assert(str == "AAA")
               assert(e1 == ex)
               assert(e2 == ex)
               assert(e3 == ex)
         }
      }

   test("quickSort") {
      for n <- 1 to 16 do
         withThreads(n) {
            1000 times {
               withThreadPoolAndWait(summon[ExecutionContext]) {
                  val len  = FastRandom.nextInt(1000)
                  val max  = 1.max(len / 2)
                  val nums = List.fill(len)(FastRandom.nextInt(max))
                  for list <- quickSort(nums) yield assert(list == nums.sorted)
               }
            }
         }
   }
