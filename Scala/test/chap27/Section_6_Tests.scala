package chap27

import chap27.Section_6.*
import mocks.Chap25.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import reactor.core.publisher.Flux
import tinyscalautils.threads.withThreads
import tinyscalautils.timing.timeIt

import scala.concurrent.duration.MILLISECONDS
import scala.jdk.CollectionConverters.ListHasAsScala

class Section_6_Tests extends AnyFunSuite with Tolerance:
   private val someConnection = Connection(1)
   private val requests       = Flux.just(Request(1), Request(2))
   private val expected = Set(1, 2).flatMap(i => "FDP".map(c => s"${c.toString}($i)")) + "W(1)"

   for (n, t) <- Seq(1 -> 8.0, 2 -> 5.0, 3 -> 4.0) do
      test(s"fetch ad (n = $n)") {
         serverOutput.clear()
         withThreads(n) {
            makePages(requests).doOnNext(someConnection.write).subscribe()
            val (received, time) = timeIt(Set.fill(8)(serverOutput.take()))
            assert(serverOutput.poll(1500, MILLISECONDS) eq null)
            assert(received == expected)
            assert(time === t +- 0.1)
         }
      }

   test("window") {
      val list = Demo1Test().largestPastHour.take(4).collectList().block().asScala
      assert(list == List(2, 3, 3, 1))
   }
