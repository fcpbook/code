package chap24

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ Execute, countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.{ CountDownLatch, CyclicBarrier, LinkedBlockingQueue }
import scala.collection.mutable
import scala.concurrent.{ Await, Future }

abstract class AsyncRunnerTests[R[_] : Runner] extends AnyFunSuite:
   private class SyncComp extends (Int => Unit):
      private val latches = mutable.Map.empty[Int, CountDownLatch]
      private val calls   = LinkedBlockingQueue[Int]()

      def apply(n: Int): Unit =
         synchronized {
            if isRunning(n) then throw IllegalArgumentException("duplicated input")
            val latch = CountDownLatch(1)
            latches(n) = latch
            calls.put(n)
            notifyAll()
            latch
         }.await()

      def isRunning(n: Int): Boolean = synchronized(latches.get(n).exists(_.getCount > 0))

      def finish(n: Int): Unit = synchronized {
         latches.remove(n).getOrElse(throw IllegalArgumentException("no such input")).countDown()
      }

      def waitToStart(n: Int): Unit = synchronized(while !isRunning(n) do wait())

      def waitToCall(): Int = calls.take()
   end SyncComp

   test("basic") {
      val comp   = SyncComp()
      val runner = summon[Runner[R]].newRunner(global, comp)
      withThreadPoolAndWait(global) {
         Execute(runner.run(Seq(1)))
         comp.waitToStart(1)
         val f = Future(runner.run(Seq(2)))
         assert(runner.addInput(3))
         comp.finish(1)
         comp.waitToStart(3)
         assert(runner.addInput(4))
         comp.finish(3)
         comp.waitToStart(4)
         assert(!comp.isRunning(2))
         comp.finish(4)
         comp.waitToStart(2)
         comp.finish(2)
         f
      }
      assert(!runner.addInput(0))
   }

   test("concurrent runs") {
      val inputs = Seq.range(-32, 0)
      for n <- Seq.iterate(1, 7)(_ * 2) do
         val comp   = SyncComp()
         val runner = summon[Runner[R]].newRunner(global, comp)
         val start  = CountDownLatch(n)
         for id <- 1 to n do
            Execute {
               start.countDownAndWait()
               runner.run(inputs :+ id)
            }
         n times {
            var i = 0
            while i <= 0 do i = comp.waitToCall()
            for j <- 1 to n do assert(comp.isRunning(j) == (j == i))
            comp.finish(i)
            for input <- inputs do
               comp.waitToStart(input)
               comp.finish(input)
         }
   }

   test("concurrent runs/addInput") {
      val inputs = Seq.range(1, 32)
      for n <- Seq.iterate(1, 7)(_ * 2) do
         val comp    = SyncComp()
         val runner  = summon[Runner[R]].newRunner(global, comp)
         val start   = CountDownLatch(n)
         val barrier = CyclicBarrier(2)
         n times {
            Execute {
               start.countDownAndWait()
               runner.run(inputs)
            }
         }
         Execute {
            n times {
               barrier.await()
               if runner.addInput(-1) then
                  var i = 0
                  while i != -1 do i = comp.waitToCall()
                  comp.finish(-1)
            }
         }
         n times {
            for input <- inputs do
               comp.waitToStart(input)
               comp.finish(input)
            barrier.await()
         }
   }

class Async1RunnerTests extends AsyncRunnerTests[Section_8.SingleCondition.Runner]
class Async2RunnerTests extends AsyncRunnerTests[Section_8.TwoConditions.Runner]
class Async3RunnerTests extends AsyncRunnerTests[Section_9.Runner]
