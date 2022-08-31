package chap27

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern.*
import akka.util.Timeout
import chap27.Section_5.*
import mocks.Chap25.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in
import tinyscalautils.control.times
import tinyscalautils.threads.{ Execute, withThreadPoolAndWait }
import tinyscalautils.timing.timeIt

import java.time.Duration
import java.util.concurrent.CountDownLatch
import scala.concurrent.duration.{ DurationInt, MILLISECONDS }
import scala.concurrent.{ ExecutionContext, Future }

class Section_5_Tests extends AnyFunSuite with Tolerance:
   private val someConnection = Connection(1)
   private val someRequest    = Request(1)

   given Timeout = Timeout(5.minutes)
   System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN")

   test("fetch ad") {
      given system: ActorSystem[RequestMsg] = ActorSystem(requestHandling(), "demo")
      given ExecutionContext                = system.executionContext
      serverOutput.clear()
      system
         .ask[PageMsg](RequestMsg(_, someRequest))
         .foreach(msg => someConnection.write(msg.page))
      val (received, time) = timeIt(Seq.fill(4)(serverOutput.take()))
      assert(time === 3.0 +- 0.1)
      assert(serverOutput.poll(1500, MILLISECONDS) eq null)
      assert(
        received in Set(Seq("F(1)", "D(1)", "P(1)", "W(1)"), Seq("D(1)", "F(1)", "P(1)", "W(1)"))
      )
      system.terminate()
   }

   test("start/stop") {
      given system: ActorSystem[Command] = ActorSystem(reset(), "demo")

      withThreadPoolAndWait(system.executionContext) {
         val n                      = 1_000_000
         val future: Future[Number] = system.ask(Start.apply)
         val end                    = CountDownLatch(n)
         n times Execute {
            system ! Number(1)
            end.countDown()
         }
         end.await()
         system ! Stop
         future.map(number => assert(number.value == 1_000_000))
      }
   }
