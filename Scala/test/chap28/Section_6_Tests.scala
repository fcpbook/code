package chap28

import chap28.Section_6.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.*
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.{ delay, timeIt, timeOf }
import tinyscalautils.util.FastRandom

import java.util.concurrent.CompletableFuture
import scala.jdk.FutureConverters.CompletionStageOps

class Section_6_Tests extends AnyFunSuite with Tolerance:
   test("empty") {
      assert(findFirst(Seq.empty[CompletableFuture[Int]], _ => true).get().isEmpty)
   }

   test("small") {
      val future =
         val f1 = CompletableFuture.supplyAsync(() => delay(1)("A1"), global)
         val f2 = CompletableFuture.supplyAsync(() => delay(2)("B2"), global)
         val f3 = CompletableFuture.supplyAsync(() => delay(3)("B3"), global)
         findFirst(Seq(f1, f2, f3), _.startsWith("B"))
      val (opt, time) = timeIt(future.get())
      assert(opt.contains("B2"))
      assert(time === 2.0 +- 0.1)
   }

   for n <- Seq(10, 50, 100, 200, 500) do
      test(s"large (n = $n)") {
         val futures =
            val fast = Seq.fill(n / 2)(FastRandom.between(0.0f, 1.5f) -> 1)
            val slow = Seq.fill(n / 2)(FastRandom.between(2.5f, 4.0f) -> 2)
            val all  = FastRandom.shuffle(((2.0f -> 3) +: slow) ++ fast)
            all.map((d, s) => CompletableFuture.supplyAsync(() => delay(d)(s), global))
         val future      = findFirst(futures, _ > 1)
         val (opt, time) = timeIt(future.get())
         assert(opt.contains(3))
         assert(time === 2.0 +- 0.1)
      }

   test("success") {
      val time = timeOf {
         withThreadPoolAndWait(global) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('A', 2.5))
            assert(shortTime === 0.0 +- 0.1)
            future.asScala.map(result => assert(result.contains('A')))
         }
      }
      assert(time === 2.0 +- 0.1)
   }

   test("failure") {
      val time = timeOf {
         withThreadPoolAndWait(global) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('B', 10.0))
            assert(shortTime === 0.0 +- 0.1)
            future.asScala.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 2.0 +- 0.1)
   }

   test("timeout 1") {
      val time = timeOf {
         withThreadPoolAndWait(global) {
            val runner              = Runner(Seq(S1, S2, S3), global)
            val (future, shortTime) = timeIt(runner.compute('A', 1.0))
            assert(shortTime === 0.0 +- 0.1)
            future.asScala.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 1.0 +- 0.1)
   }

   test("timeout 2") {
      val exec = Executors.newThreadPool(availableProcessors)
      val time = timeOf {
         withThreadPoolAndWait(exec) {
            val runner              = Runner(Seq.fill(100)(S3), exec)
            val (future, shortTime) = timeIt(runner.compute('B', 1.0))
            assert(shortTime === 0.0 +- 0.1)
            future.asScala.map(result => assert(result.isEmpty))
         }
      }
      assert(time === 1.0 +- 0.1)
      assert(exec.shutdownAndWait(0.1))
   }
