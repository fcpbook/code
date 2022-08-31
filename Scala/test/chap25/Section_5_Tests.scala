package chap25

import chap25.Section_5.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.threads.withMarkedThreads
import tinyscalautils.threads.isMarkedThread
import tinyscalautils.timing.sleep

import java.util.concurrent.CountDownLatch
import scala.concurrent.ExecutionException

class Section_5_Tests extends AnyFunSuite:
   for failed <- Seq(false, true) do
      test(s"Scala (failed = $failed)") {
         import scala.concurrent.Future

         withMarkedThreads() {
            val E     = Exception()
            val latch = CountDownLatch(1)
            val f: Future[Thread] = Scala {
               latch.await()
               if failed then throw E else Thread.currentThread
            }
            sleep(0.5)
            assert(!f.isCompleted)
            latch.countDown()
            f.map(thread => assert(thread.isMarkedThread)).recover { case E => succeed }
         }
      }

      test(s"JavaNew (failed = $failed)") {
         import java.util.concurrent.Future

         withMarkedThreads() {
            val E     = Exception()
            val latch = CountDownLatch(1)
            val f: Future[Thread] = JavaNew {
               latch.await()
               if failed then throw E else Thread.currentThread
            }
            sleep(0.5)
            assert(!f.isDone)
            latch.countDown()
            try assert(f.get().isMarkedThread)
            catch case ex: ExecutionException => assert(ex.getCause eq E)
            scala.concurrent.Future.unit
         }
      }

      test(s"JavaOld (failed = $failed)") {
         import java.util.concurrent.Future

         withMarkedThreads() {
            val E     = Exception()
            val latch = CountDownLatch(1)
            val f: Future[Thread] = JavaOld {
               latch.await()
               if failed then throw E else Thread.currentThread
            }
            sleep(0.5)
            assert(!f.isDone)
            latch.countDown()
            try assert(f.get().isMarkedThread)
            catch case ex: ExecutionException => assert(ex.getCause eq E)
            scala.concurrent.Future.unit
         }
      }
