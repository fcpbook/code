package chap25

import chap25.Section_2.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite

class Section_2_Tests extends AnyFunSuite, ServerChecks, Tolerance:
   test("demo") {
      demo(b => assert(b))
   }

   test("serverSeq") {
      val n               = 16 // at most the pool size
      val server          = ServerSeq()
      val (strings, time) = testServer(1 to n, server.demo(), server.server.getLocalPort)
      server.server.close()
      server.exec.shutdown()
      assert(time === 8.0 +- 0.5)
      for (group, char) <- strings.grouped(n).zip("RDLFPWCS".toSeq) do checkSet(group, char, n)
   }

   test("serverPar") {
      val n               = 8 // at most the second pool size
      val server          = ServerPar()
      val (strings, time) = testServer(1 to n, server.demo(), server.server.getLocalPort)
      server.server.close()
      server.exec1.shutdown()
      server.exec2.shutdown()
      assert(time === 7.0 +- 0.5)
      val groups = strings.grouped(n).toIndexedSeq
      val (d, f) = (groups(1) ++ groups(2)).sorted.splitAt(n)
      for (group, char) <- (Seq(groups(0), d, f) ++ groups.slice(3, 8)).zip("RDFLPWCS".toSeq) do
         checkSet(group, char, n)
   }

   for n <- Seq(1, 2, 3, 5, 10, 12, 16, 20, 50) do
      test(s"serverSeq (n=$n)") {
         val server          = ServerSeq()
         val (strings, time) = testServer(1 to n, server.demo(), server.server.getLocalPort)
         server.server.close()
         server.exec.shutdown()
         val t = n / 16 + (if n % 16 == 0 then 0 else 1)
         assert(time === t * 8.0 +- 0.5)
         check(strings, n)
      }

      test(s"serverPar (n=$n)") {
         val server          = ServerPar()
         val (strings, time) = testServer(1 to n, server.demo(), server.server.getLocalPort)
         server.server.close()
         server.exec1.shutdown()
         server.exec2.shutdown()
         val t = n / 12 + (if n % 12 == 0 then 0 else 1)
         assert(time === t * 7.0 +- 0.5)
         check(strings, n)
      }
