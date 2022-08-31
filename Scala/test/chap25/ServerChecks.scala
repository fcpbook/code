package chap25

import mocks.Chap25.serverOutput
import org.scalatest.{ Suite, SuiteMixin }
import tinyscalautils.threads.Execute
import tinyscalautils.threads.Executors.global
import tinyscalautils.timing.timeIt

import java.io.OutputStreamWriter
import java.net.Socket
import scala.concurrent.Future
import scala.concurrent.duration.MILLISECONDS
import scala.util.Using

trait ServerChecks extends SuiteMixin:
   self: Suite =>

   def testServer(ids: Seq[Int], serverCode: => Any, port: Int): (Seq[String], Double) =
      serverOutput.clear()
      val len = ids.length * 8 - 3 * ids.count(_ < 0)
      Future(serverCode) // fails with closing exception
      for i <- ids do
         Execute {
            val s = Socket("localhost", port)
            Using(OutputStreamWriter(s.getOutputStream))(out => out.write(i.toString + '\n'))
         }
      val received = timeIt(Seq.fill(len)(serverOutput.take()))
      assert(serverOutput.poll(1500, MILLISECONDS) eq null)
      received

   def checkSet(seq: Seq[String], char: Char, n: Int) =
      val str = char.toString
      assert(seq.size == n)
      assert(seq.toSet == Set.tabulate(n)(i => s"$str(${i + 1})"))

   def check(seq: Seq[String], n: Int) =
      for i <- 1 to n do
         def indexOf(c: Char)            = seq.indexWhere(_ == s"${c.toString}($i)")
         val Seq(r, d, l, f, p, w, c, s) = "RDLFPWCS".toSeq.map(indexOf)
         assert(r >= 0 && r < f && r < d && f < p && d < p && d < l && p < w && p < s && w < c)
