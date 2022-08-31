package chap27

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.system.measureNanoTime
import kotlin.test.Test
import kotlin.test.assertEquals

class FetchAdTests {
  private fun testDemo(exec: ExecutorService): Double {
    return measureNanoTime {
      runBlocking(exec.asCoroutineDispatcher()) {
        val shortTime = measureNanoTime {
          launch { demo(0) }
        } / 1e9
        assertEquals(0.0, shortTime, 0.1)
      }
    } / 1e9
  }

  @Test
  fun testDemo1() {
    val exec = Executors.newSingleThreadExecutor()
    assertEquals(3.0 + 2.0, testDemo(exec), 0.1)
    exec.shutdown()
  }

  @Test
  fun testDemo2() {
    val exec = Executors.newFixedThreadPool(2)
    assertEquals(max(3.0, 2.0), testDemo(exec), 0.1)
    exec.shutdown()
  }
}
