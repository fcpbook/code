package chap19

import chap19.Section_1.*
import mocks.Chap19.semaphore
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times

class Section_1_Tests extends AnyFunSuite:
   test("demo") {
      demo()
      semaphore.acquire(4)
      1_000_000 times assert(semaphore.availablePermits() == 0)
   }
