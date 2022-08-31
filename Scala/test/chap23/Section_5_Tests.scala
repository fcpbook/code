package chap23

import chap23.Section_5.*
import mocks.Chap23.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.{ Executors, shutdownAndWait }

class Section_5_Tests extends AnyFunSuite:
   private def checkOut() = assert(outWritten.linesIterator.toSet == matches)

   test(s"demo1 ($fileCount files, $N threads)") {
      val exec = Executors.newUnlimitedThreadPool()
      demo1(exec)
      exec.shutdownAndWait()
      checkOut()
   }

   test(s"demo2 ($fileCount files, $N threads)") {
      val exec = Executors.newUnlimitedThreadPool()
      demo2(exec)
      exec.shutdownAndWait()
      checkOut()
   }
