package chap21

import chap21.Section_2.*
import mocks.Chap21.serverLines
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Execute
import tinyscalautils.threads.Executors.global

import java.io.OutputStreamWriter
import java.net.Socket
import scala.concurrent.Future
import scala.concurrent.duration.MILLISECONDS
import scala.util.Using

class Section_2_Tests extends AnyFunSuite:
   private def testServer(serverCode: => Any, port: Int) =
      val n = 10
      Future(serverCode) // fails with closing exception
      for i <- 0 until n do
         Execute {
            val s = Socket("localhost", port)
            Using(OutputStreamWriter(s.getOutputStream))(out => out.write(i.toString + '\n'))
         }
      val received = Set.fill(n)(serverLines.take().toInt)
      assert(received == Set.range(0, n))
      assert(serverLines.poll(1500, MILLISECONDS) eq null)

   test("sequential") {
      val server = SeqServer()
      testServer(server.demo(), server.server.getLocalPort)
      server.server.close()
   }

   test("threads") {
      val server = ParServerThread()
      testServer(server.demo(), server.server.getLocalPort)
      server.server.close()
   }

   test("pool") {
      val server = ParServerPool()
      testServer(server.demo(), server.server.getLocalPort)
      server.server.close()
      server.exec.shutdown()
   }
