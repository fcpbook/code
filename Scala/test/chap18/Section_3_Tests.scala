package chap18

import chap18.Section_3.*
import org.scalatest.concurrent.{ Signaler, ThreadSignaler, TimeLimits }
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ availableProcessors, countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class Section_3_Tests extends AnyFunSuite with TimeLimits:
   given Signaler = ThreadSignaler

   test("Demo1") {
      val n = availableProcessors.max(6)
      @tailrec
      def tryFailure(count: Int): Unit =
         val success = withThreadPoolAndWait(global) {
            val rank = Demo1()
            Future.traverse(1 to n)(_ => Future(rank.getRank())).map(_.sorted == (1 to n))
         }
         if success then println(count) else tryFailure(count + 1)
      cancelAfter(1.minute)(tryFailure(1))
   }

   test("Demo2") {
      val n = availableProcessors
      1000 times {
         withThreadPoolAndWait(global) {
            val start = CountDownLatch(n)
            val rank  = Demo2()
            Future
               .traverse(1 to n) { _ =>
                  Future {
                     start.countDownAndWait()
                     rank.getRank()
                  }
               }
               .map(ranks => assert(ranks.sorted == (1 to n)))
         }
      }
   }

   test("Demo3") {
      val n = availableProcessors.max(6)
      @tailrec
      def tryFailure(count: Int): Unit =
         val success = withThreadPoolAndWait(global) {
            val rank = Demo3()
            Future.traverse(1 to n)(_ => Future(rank.getRank())).map(_.count(_.nonEmpty) == 5)
         }
         if success then println(count) else tryFailure(count + 1)
      cancelAfter(1.minute)(tryFailure(1))
   }
