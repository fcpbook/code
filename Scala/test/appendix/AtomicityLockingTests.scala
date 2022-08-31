package appendix

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ availableProcessors, countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future
import scala.jdk.OptionConverters.RichOptionalInt

class AtomicityLockingTests extends AnyFunSuite:
   import AtomicityLockingJ.*

   private def testGetRank[A](make: () => A, getRank: A => Option[Int]) =
      val n        = availableProcessors.max(6)
      val expected = Set.tabulate(5)(x => Some(x + 1)) + None
      10_000 times {
         val set = withThreadPoolAndWait(global) {
            val start = CountDownLatch(n)
            val rank  = make()
            Future.traverse(Set.range(0, n)) { _ =>
               Future {
                  start.countDownAndWait()
                  getRank(rank)
               }
            }
         }
         assert(set == expected)
      }

   test("LockObject.getRank") {
      testGetRank(() => LockObject(), _.getRank().toScala)
   }

   test("LockMethod.getRank") {
      testGetRank(() => LockMethod(), _.getRank().toScala)
   }

   test("LockThis.getRank") {
      testGetRank(() => LockThis(), _.getRank().toScala)
   }
