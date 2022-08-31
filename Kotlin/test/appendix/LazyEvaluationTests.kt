package appendix

import appendix.LazyEvaluation.collatz
import appendix.LazyEvaluation.demo1
import appendix.LazyEvaluation.demo2
import appendix.LazyEvaluation.demo3
import appendix.LazyEvaluation.time
import appendix.LazyEvaluation.variable
import mocks.computed
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LazyEvaluationTests {
  @Test
  fun testList() {
    assertEquals(listOf("X", "XX", "XXX", "XXXX", "XXXXX"), demo1())
    fun loop(demo: () -> List<Int>): Boolean = demo().toSet().size != 1 || loop(demo)
    assertTrue(loop(::demo2))
    assertTrue(loop(::demo3))
  }

  @Test
  fun testTimeOf() {
    assertEquals(1.0, time, 0.1)
  }

  @Test
  fun testCollatz() {
    assertEquals(111, collatz(BigInteger("27")))
    val n = BigInteger("992774507105260663893249807781832616822016143650134730933270")
    assertEquals(2632, collatz(n))
  }

  @Test
  fun testLazy() {
    computed = 0
    assertEquals(1, variable)
    assertEquals(1, computed)
    assertEquals(1, variable)
    assertEquals(1, computed)
  }
}
