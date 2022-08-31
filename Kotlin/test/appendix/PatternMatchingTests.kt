package appendix

import appendix.PatternMatching.TryPatterns
import kotlin.test.Test
import kotlin.test.assertEquals

class PatternMatchingTests {
  @Test
  fun testPatterns() {
    assertEquals("One", TryPatterns(1).str)
    assertEquals("Two", TryPatterns(2).str)
    assertEquals("Two", TryPatterns("2").str)
    assertEquals("A list", TryPatterns(listOf(1,2,3)).str)
    assertEquals("Not a string", TryPatterns(3).str)
    assertEquals("Unknown", TryPatterns("3").str)
  }
}
