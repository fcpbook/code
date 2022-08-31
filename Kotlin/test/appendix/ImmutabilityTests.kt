package appendix

import appendix.Immutability.b
import appendix.Immutability.demo
import appendix.Immutability.parseVerbosity
import kotlin.test.Test
import kotlin.test.assertEquals

class ImmutabilityTests {
  @Test
  fun testParseVerbosity() {
    assertEquals(0, parseVerbosity(""))
    assertEquals(1, parseVerbosity("-v"))
    assertEquals(2, parseVerbosity("-vv"))
  }

  @Test
  fun testDemo1() {
    assertEquals(20, demo())
  }

  @Test
  fun testDemo2() {
    assertEquals(listOf("B", "C"), b)
  }
}
