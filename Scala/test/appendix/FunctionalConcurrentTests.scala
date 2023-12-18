package appendix

import chap25.ServerChecks
import org.scalatest.funsuite.AnyFunSuite

class FunctionalConcurrentTests extends AnyFunSuite with ServerChecks:
   import FunctionalConcurrentJ.*
   for n <- Seq(1, 2, 3, 5, 10, 12, 16, 20, 50) do
      test(s"Java server (n=$n)") {
         val server = Server()
         try
            val (strings, _) = testServer(1 to n, server.demo(), server.server.getLocalPort)
            check(strings, n)
         finally
            server.server.close()
            server.exec.shutdown()
      }
