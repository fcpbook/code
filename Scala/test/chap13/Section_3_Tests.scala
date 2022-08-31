package chap13

import chap13.Section_3.*
import mocks.Chap13.{ notFound, wordFile }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.withThreadPoolAndWait

import java.util.concurrent.CompletableFuture
import scala.concurrent.{ Future, Promise }
import scala.util.Try

class Section_3_Tests extends AnyFunSuite:
   test("demos") {
      def checkFailure(t: Try[?]) = t.isFailure && t.failed.get.getMessage == "words"
      assert(demo1.get == List("two", "three"))
      assert(checkFailure(demo2))
      assert(demo3(wordFile).get == 4)
      assert(checkFailure(demo3(notFound)))
      assert(demo4(wordFile).getOrElse(4) == 4)
      assert(checkFailure(demo4(notFound)))
      assert(demo5(wordFile).get == chap13.Section_1.words)
      assert(demo5(notFound).isEmpty)
   }

   test("demo6 success") {
      withThreadPoolAndWait(global) {
         val f = Future(42)
         val p = Promise[Int]()
         demo6(f, n => p.success(n), e => p.failure(e))
         p.future.map(n => assert(n == 42))
      }
   }

   test("demo6 failure") {
      withThreadPoolAndWait(global) {
         val ex = NullPointerException()
         val f  = Future[Int](throw ex)
         val p  = Promise[Int]()
         demo6(f, n => p.success(n), e => p.failure(e))
         p.future.failed.map(e => assert(e eq ex))
      }
   }

   test("demo Java success") {
      withThreadPoolAndWait(global) {
         val f = CompletableFuture.supplyAsync(() => 42: Integer)
         val p = Promise[Int]()
         Section_3J.demo(f, n => p.success(n), e => p.failure(e))
         p.future.map(n => assert(n == 42))
      }
   }

   test("demo Java failure") {
      withThreadPoolAndWait(global) {
         val ex = NullPointerException()
         val f  = CompletableFuture.supplyAsync(() => (throw ex): Integer)
         val p  = Promise[Int]()
         Section_3J.demo(f, n => p.success(n), e => p.failure(e))
         p.future.failed.map(e => assert(e.getCause eq ex))
      }
   }
