package chap02

import kotlin.test.*

class Section_1_Tests {
  @Test
  fun testAbs() {
    assertEquals(42, abs(42))
    assertEquals(42, abs(-42))
  }
}
