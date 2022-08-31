package chap21

import chap21.Section_1.*
import org.scalatest.funsuite.AnyFunSuite
import util.invokeAndWait

class Section_1_Tests extends AnyFunSuite:
   test("demo1") {
      val lines = invokeAndWait(demo1().shutdown(), waitForChildren = true)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.contains("main: END"))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": A")))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": B")))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": C")))
   }

   test("demo2") {
      val lines = invokeAndWait(demo2(), waitForChildren = true)
      assert(lines.length == 5)
      assert(lines.head == "main: START")
      assert(lines.last == "main: END")
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": A")))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": B")))
      assert(lines.exists(line => line.startsWith("pool-") && line.endsWith(": C")))
   }

   test("demo3") {
      for n <- 1 to 64 do
         mocks.Chap21.N = n
         demo3(b => assert(b))
   }
