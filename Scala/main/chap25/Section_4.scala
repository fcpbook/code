package chap25

import tinyscalautils.threads.Executors.global as exec

object Section_4:
   def demo1(assert: Boolean => Unit): Unit =
      import java.util.concurrent.{ CompletableFuture, Future }

      def makeStrings(n: Int, str: String): List[String] = List.fill(n)(str)

      val f1: Future[List[String]] = CompletableFuture.supplyAsync(() => makeStrings(5, "T1"), exec)
      val f2: Future[List[String]] = CompletableFuture.supplyAsync(() => makeStrings(5, "T2"))

      val strings: List[String] = f1.get() ::: f2.get()
      assert(strings.size == 10)
   end demo1

   def demo2(assert: Boolean => Unit): Unit =
      import scala.concurrent.duration.Duration
      import scala.concurrent.{ Await, ExecutionContext, Future }

      given ExecutionContext = exec

      def makeStrings(n: Int, str: String): List[String] = List.fill(n)(str)

      val f1: Future[List[String]] = Future(makeStrings(5, "T1"))
      val f2: Future[List[String]] = Future(makeStrings(5, "T2"))

      val strings1: List[String] = Await.result(f1, Duration.Inf)
      val strings2: List[String] = Await.result(f2, Duration.Inf)
      val strings: List[String]  = strings1 ::: strings2
      assert(strings.size == 10)
   end demo2
