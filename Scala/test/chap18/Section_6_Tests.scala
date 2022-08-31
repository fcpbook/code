package chap18

import chap18.Section_6.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ availableProcessors, countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class Section_6_Tests extends AnyFunSuite:
   test("Demo1") {
      val n = availableProcessors.max(6)
      1000 times {
         withThreadPoolAndWait(global) {
            val start = CountDownLatch(n)
            val rank  = Demo1()
            Future
               .traverse(1 to n) { _ =>
                  Future {
                     start.countDownAndWait()
                     rank.getRank()
                  }
               }
               .map { ranks =>
                  assert(ranks.count(_.isEmpty) == n - 5)
                  assert(ranks.flatten.sorted == (1 to 5))
               }
         }
      }
   }

   test("register and play") {
      withThreadPoolAndWait(global) {
         val f1 = Future(Demo2.getRank())
         val f2 = Future(Demo2.play(1))
         f1 zip f2
      }
   }
