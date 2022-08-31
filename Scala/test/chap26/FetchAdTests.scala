package chap26

import mocks.Chap25.serverOutput
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.duration.MILLISECONDS

class FetchAdTests extends AnyFunSuite:
   private val seq1 = Seq("F(0)", "D(0)", "P(0)", "W(0)")
   private val seq2 = Seq("D(0)", "F(0)", "P(0)", "W(0)")

   for (demo, name) <- Seq(
        (() => Section_1.demo2(), "Section 1"),
        (() => Section_2.demo2(), "Section 2"),
        (() => Section_5.demo1(), "NonBlocking2"),
        (() => Section_5.demo2(), "NonBlocking3"),
      )
   do
      test(name) {
         serverOutput.clear()
         demo()
         val received = Seq.fill(4)(serverOutput.take())
         assert(serverOutput.poll(500, MILLISECONDS) eq null)
         assert(received == seq1 || received == seq2)
      }
