package chap23

import chap23.Section_4.*
import org.scalatest.Suites

class Section_4_Tests
    extends Suites(
      QueueTests[WaitNotifyAll.BoundedQueue[Int]](),
      QueueTests[BoundedQueue[Int]]()
    )
