package chap26

import chap26.Section_6.*
import mocks.Chap25.{ Page, Request }
import mocks.Chap26.PageException
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.withThreadPoolAndWait

import java.util.concurrent.CompletableFuture.supplyAsync
import scala.concurrent.Future
import scala.util.Success

class Section_6_Tests extends AnyFunSuite:
   test("demo") {
      withThreadPoolAndWait(global) {
         for
            e0 <- demo1(0).failed
            e1 <- demo1(1).failed
            e2 <- demo1(2).failed
            n  <- demo1(3)
         yield
            assert(e0.getMessage == "no user")
            assert(e1.getMessage == "key not found: 0")
            assert(e2.getMessage == "key not found: 1")
            assert(n == 0)
      }
   }

   test("getAccount") {
      assert(Variant1.getAccount(2).value.contains(Success(1)))
      assertThrows[NoSuchElementException](Variant1.getAccount(1))
      assert(getAccount(2).value.contains(Success(1)))
      assert(getAccount(1).value.get.isFailure)
      assertThrows[NoSuchElementException](getAccount(1).value.get.get)
   }

   test("recover") {
      withThreadPoolAndWait(global) {
         val ex = Exception()
         for
            page1 <- Demo2(Future(Page(1))).safePageF
            page2 <- Demo2(Future(throw PageException())).safePageF
            e     <- Demo2(Future(throw ex)).safePageF.failed
         yield
            assert(page1.id == 1)
            assert(page2.id == -1)
            assert(e == ex)
      }
   }

   test("map") {
      assert(map(Some(0), x => x + 1) == Some(0).map(x => x + 1))
      assert(map(Option.empty[Int], x => x + 1) == Option.empty[Int].map(x => x + 1))
   }

   test("f") {
      withThreadPoolAndWait(global) {
         for value <- f yield assert(value == (42, "foo", 1.2))
      }
   }

   test("queryDB, variant") {
      withThreadPoolAndWait(global) {
         for page <- Variant2.queryDB(List(Request(1), Request(2))) yield assert(page.id == 3)
      }
   }

   test("queryDB") {
      withThreadPoolAndWait(global) {
         for page <- queryDB(List(Request(1), Request(2))) yield assert(page.id == 3)
      }
   }

   test("sequence, empty") {
      val list = sequence(List.empty).get()
      assert(list.isEmpty)
   }

   test("sequence, small") {
      val list = sequence(List(supplyAsync(() => "foo"), supplyAsync(() => "bar"))).get()
      assert(list == List("foo", "bar"))
   }

   test("sequence, large") {
      val n    = 1000
      val list = sequence(List.tabulate(n)(i => supplyAsync(() => i))).get()
      assert(list == List.range(0, n))
   }
