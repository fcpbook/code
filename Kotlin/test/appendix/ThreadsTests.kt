package appendix

import appendix.Threads.demo
import mocks.out
import kotlin.test.Test
import kotlin.test.assertContains

class ThreadsTests {
  @Test
  fun testThreads() {
    out.clear()
    for (thread in demo()) thread.join()
    assertContains(setOf("A\nB\n", "B\nA\n"), out.toString())
  }
}
