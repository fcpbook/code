package appendix

import appendix.HandlingFailures.linesOrNull
import appendix.HandlingFailures.linesOrEmpty
import appendix.HandlingFailures.linesOrOther
import appendix.HandlingFailures.demo1
import appendix.HandlingFailures.demo2
import appendix.HandlingFailures.lines
import appendix.HandlingFailures.SafeCall
import appendix.HandlingFailures.Elvis
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertSame

class HandlingFailuresTests {
  @Test
  fun testLines() {
    lines.onSuccess { assertEquals(listOf("A"), it) }
    lines.onFailure { assertSame(mocks.ex, it) }
    assertContains(setOf(listOf("A"), null), linesOrNull)
    assertContains(setOf(listOf("A"), emptyList()), linesOrEmpty)
    assertContains(setOf(listOf("A"), listOf("B")), linesOrOther)
  }

  @Test
  fun testMap() {
    demo1().onSuccess { assertEquals(1, it) }
    demo1().onFailure { assertSame(mocks.ex, it) }
    demo2().onSuccess { assertEquals(1, it) }
    demo2().onFailure { assertSame(mocks.ex, it) }
  }

  @Test
  fun testDemo3() {
    assertContains(setOf("A", "42", null), SafeCall.optStr)
    assertContains(setOf(42, null), SafeCall.optInt)
  }

  @Test
  fun testDemo4() {
    assertContains(setOf("a", "42", "B"), Elvis.str)
    assertContains(setOf("a", "42", "B", null), Elvis.optStr)
  }
}
