package appendix

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.annotation.tailrec
import scala.concurrent.Future
import scala.jdk.CollectionConverters.SetHasAsScala

class ThreadSafeTests extends AnyFunSuite:
   test("SafeSet (Scala)") {
      val range = 1 to 1_000_000
      withThreadPoolAndWait(global) {
         val set   = ThreadSafe.SafeSet[Int]()
         val start = CountDownLatch(2)
         val f1 = Future {
            start.countDownAndWait()
            for i <- range do assertResult(i)(set += i)
         }
         val f2 = Future {
            start.countDownAndWait()
            @tailrec def loop(): Unit =
               val all = set.all
               assert(all.forall(_ in range))
               if all.size < range.size then loop()
            loop()
         }
         f1.zip(f2)
      }
   }

   test("SafeSet (Java)") {
      val range = 1 to 1_000_000
      withThreadPoolAndWait(global) {
         val set   = ThreadSafeJ.SafeSet[Int]()
         val start = CountDownLatch(2)
         val f1 = Future {
            start.countDownAndWait()
            for i <- range do assertResult(i)(set.add(i))
         }
         val f2 = Future {
            start.countDownAndWait()
            @tailrec def loop(): Unit =
               val all = set.all.asScala
               assert(all.forall(_ in range))
               if all.size < range.size then loop()
            loop()
         }
         f1.zip(f2)
      }
   }
