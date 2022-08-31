package appendix

import chap25.ServerChecks
import mocks.Chap25.Page
import mocks.Chap26.PageException
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.withUnlimitedThreadsAndWait

import scala.concurrent.Future

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
