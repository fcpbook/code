package chap21

import chap21.Section_3.*
import org.scalatest.funsuite.AnyFunSuite
import util.invokeAndWait

import scala.concurrent.duration.SECONDS

class Section_3_Tests extends AnyFunSuite:
   test("demo") {
      val N = 6 // 1 minute
      val lines = invokeAndWait(
        {
           val exec = demo()
           exec.schedule((() => exec.shutdown()): Runnable, N * 10, SECONDS)
        },
        waitForChildren = true
      )
      assert(lines.length == N * 2 + 3)
      assert(lines.head == "main: START")
      assert(lines.contains("main: END"))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": A")))
      assert(lines.count(line => line.startsWith("pool-") && line.endsWith(": B")) == N)
      assert(lines.count(line => line.startsWith("pool-") && line.endsWith(": C")) == N)
   }
