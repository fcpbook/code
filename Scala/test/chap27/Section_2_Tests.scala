package chap27

import chap27.Section_2.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ countDownAndWait, withThreadPoolAndWait }
import tinyscalautils.util.FastRandom

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class Section_2_Tests extends AnyFunSuite:
   for n <- Seq.iterate(1, 7)(_ * 2); m = 1_000_000 / n do
      def testStack(push: Int => Unit, pop: () => Option[Int], peek: => Option[Int]) =
         val start = CountDownLatch(n)
         withThreadPoolAndWait(global) {
            Future
               .traverse(1 to n) { _ =>
                  Future {
                     val popped = Set.newBuilder[Int]
                     val pushed = Seq.fill(m)(FastRandom.nextInt())
                     start.countDownAndWait()
                     for num <- pushed do
                        push(num)
                        assert(peek.nonEmpty)
                        popped += pop().get
                     (pushed.toSet, popped.result())
                  }
               }
               .map { sets =>
                  val (pushed, popped) = sets.unzip
                  assert(pushed.reduce(_ union _) == popped.reduce(_ union _))
                  assert(peek.isEmpty)
               }
         }

      test(s"LockedStack ($n threads)") {
         val stack = LockStack.Stack[Int]()
         testStack(stack.push, stack.pop, stack.peek)
      }

      test(s"AtomicStack ($n threads)") {
         val stack = AtomicStack.Stack[Int]()
         testStack(stack.push, stack.pop, stack.peek)
      }
