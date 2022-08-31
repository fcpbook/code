package appendix

import appendix.RecursiveProgramming.Empty
import appendix.RecursiveProgramming.Node
import appendix.RecursiveProgramming.factorial
import appendix.RecursiveProgramming.size
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecursiveProgrammingTests {
  @Test
  fun testFactorial() {
    assertEquals(1, factorial(0))
    assertEquals(3_628_800, factorial(10))
    assertTrue(runCatching { factorial(1000) }.isSuccess)
  }

  @Test
  fun testBinTree() {
    val tree = Node(2, Node(1, Empty, Empty), Node(3, Empty, Empty))
    assertEquals(3, size(tree))
    assertEquals(1, size(tree.left))
    assertEquals(0, size(Empty))
  }
}
