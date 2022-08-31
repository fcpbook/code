package chap16

import mocks.out
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class Section_1_Tests {
  @Test
  fun testAsyncABC() {
    out.clear()
    asyncABC()
    assertContains(setOf("A\nB\nC\n", "A\nC\nB\n"), out.toString())
  }

  @Test
  fun testJoinABC() {
    out.clear()
    joinABC()
    assertEquals("A\nB\nC\n", out.toString())
  }

}
