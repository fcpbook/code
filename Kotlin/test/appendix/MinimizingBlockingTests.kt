package appendix

import appendix.MinimizingBlocking.demo
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class MinimizingBlockingTests {
  @Test
  fun testCountDownLatch() {
    for (n in 1..10)
      for (m in 1..10)
        runBlocking {
          demo(n, m).shutdown()
        }
  }
}
