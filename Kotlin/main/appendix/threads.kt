package appendix

import mocks.println
import kotlin.concurrent.thread

object Threads {
  fun demo(): List<Thread> {
    val tA = thread {
      println('A')
    }

    val tB = thread(name = "TB", start = false) {
      println('B')
    }
    tB.start()

    // so they can be joined
    return listOf(tA, tB)
  }
}
