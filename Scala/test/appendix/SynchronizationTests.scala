package appendix

import chap23.LockSemaphoreTests
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.withThreadPoolAndWait

class SynchronizationTests
    extends LockSemaphoreTests(SynchronizationJ.SimpleLock(), _.lock(), _.unlock())
