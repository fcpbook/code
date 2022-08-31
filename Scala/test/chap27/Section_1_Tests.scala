package chap27

import chap27.Section_1.*
import chap27.Section_1J.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class Section_1_Tests extends AnyFunSuite:
   for n <- Seq.iterate(1, 7)(_ * 2); m = 1_000_000 / n do
      def testInteger[A](num: A, incrGet: A => Int, get: A => Int) =
         val start = CountDownLatch(n)
         withThreadPoolAndWait(global) {
            Future
               .traverse(1 to n) { _ =>
                  Future {
                     start.countDownAndWait()
                     Seq.fill(m)(incrGet(num))
                  }
               }
               .map(seq => assert(seq.flatten.toSet == Set.range(1, n * m + 1)))
               .map(_ => assert(get(num) == n * m))
         }

      test(s"LockedInteger ($n threads)") {
         testInteger(LockedInteger(), _.incrementAndGet(), _.get)
      }

      test(s"AtomicInteger ($n threads)") {
         testInteger(AtomicInteger(), _.incrementAndGet(), _.get)
      }
