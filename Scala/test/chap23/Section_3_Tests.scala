package chap23

import chap23.Section_3.*
import org.scalatest.Suites

class Section_3_Tests
    extends Suites(
      LockSemaphoreTests(SimpleLock(), _.lock(), _.unlock()),
      QueueTests[BoundedQueue[Int]]()
    )
