package chap27

import mocks.Chap25.{ Connection, Request, serverOutput }
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.{ run, shutdownAndWait, withThreads }
import tinyscalautils.timing.timeIt

import java.util.concurrent.ForkJoinPool
import scala.concurrent.duration.MILLISECONDS

class FetchAdTests extends AnyFunSuite with Tolerance:
   private val seq1 = Seq("F(1)", "D(1)", "P(1)", "W(1)")
   private val seq2 = Seq("D(1)", "F(1)", "P(1)", "W(1)")

   private val someConnection = Connection(1)
   private val someRequest    = Request(1)

   for n <- 1 to 3 do
      test(s"fork/join (n = $n)") {
         serverOutput.clear()
         val exec = ForkJoinPool(n)
         exec.run(someConnection.write(Section_3.demo(someRequest)))
         val (received, time) = timeIt(Seq.fill(4)(serverOutput.take()))
         assert(serverOutput.poll(1500, MILLISECONDS) eq null)
         assert(received == seq1 || received == seq2)
         assert(time === (5.0 - n.min(2)) +- 0.1)
         assert(exec.shutdownAndWait(0.1))
      }

      test(s"async (n = $n)") {
         serverOutput.clear()
         withThreads(n) {
            Section_4.demo(someRequest).foreach(someConnection.write)
            val (received, time) = timeIt(Seq.fill(4)(serverOutput.take()))
            assert(serverOutput.poll(1500, MILLISECONDS) eq null)
            assert(received == seq1 || received == seq2)
            assert(time === (5.0 - n.min(2)) +- 0.1)
         }
      }
