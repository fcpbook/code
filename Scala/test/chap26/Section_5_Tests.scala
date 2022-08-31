package chap26

import chap25.ServerChecks
import chap26.Section_5.*
import org.scalatest.funsuite.AnyFunSuite

class Section_5_Tests extends AnyFunSuite with ServerChecks:
   for n <- Seq(1, 2, 3, 5, 10, 12, 16, 20, 50) do
      test(s"server (n = $n)") {
         val server = Server()
         try
            val (strings, _) = testServer(1 to n, server.demo(), server.server.getLocalPort)
            check(strings, n)
         finally
            server.server.close()
            server.exec.shutdown()
      }

      test(s"server, timeout (n = $n)") {
         val server       = ServerTimeout()
         val (strings, _) = testServer(1 to n, server.demo(), server.server.getLocalPort)
         server.server.close()
         server.exec.shutdown()
         server.timer.shutdown()
         check(strings, n)
      }

   test("bad ad") {
      val server       = ServerTimeout()
      val (strings, _) = testServer(Seq(Int.MaxValue), server.demo(), server.server.getLocalPort)
      server.server.close()
      server.exec.shutdown()
      server.timer.shutdown()
      assert(strings.contains("T(0)"))
   }

   test("error") {
      val server = ServerTimeout()
      try
         val (strings, _) = testServer(Seq(-1), server.demo(), server.server.getLocalPort)
         assert(
           strings == Seq("R(1)", "D(1)", "L(1)", "W(1)", "C(1)") ||
              strings == Seq("R(1)", "D(1)", "W(1)", "L(1)", "C(1)") ||
              strings == Seq("R(1)", "D(1)", "W(1)", "C(1)", "L(1)")
         )
      finally
         server.server.close()
         server.exec.shutdown()
   }
