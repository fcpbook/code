package appendix

import appendix.ThreadPools.demo1
import appendix.ThreadPools.demo2
import appendix.ThreadPools.demo3
import mocks.out
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ThreadPoolsTests {
  @Test
  fun testThreadPool() {
    out.clear()
    val exec = Executors.newSingleThreadExecutor { Thread(it, "Joe") }
    val end = CountDownLatch(1)
    demo1(exec, end)
    end.await()
    assertEquals("Joe\n", out.toString())
    exec.shutdown()
  }

  @Test
  fun testContext() {
    out.clear()
    val exec = Executors.newSingleThreadExecutor { Thread(it, "Joe") }
    val end = CountDownLatch(1)
    demo2(exec, end)
    end.await()
    assertTrue(out.toString().startsWith("Joe"))
    exec.shutdown()
  }

  @Test
  fun testDispatcher() {
    out.clear()
    val end = CountDownLatch(1)
    demo3(end)
    end.await()
    assertTrue(out.toString().startsWith("DefaultDispatcher"))
  }
}
