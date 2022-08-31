package appendix

import appendix.HigherOrderFunctions.Neg1
import appendix.HigherOrderFunctions.Neg2
import appendix.HigherOrderFunctions.Neg3
import appendix.HigherOrderFunctions.Len1
import appendix.HigherOrderFunctions.Len2
import appendix.HigherOrderFunctions.e
import appendix.HigherOrderFunctions.existsOrEmpty
import appendix.HigherOrderFunctions.byLength
import appendix.HigherOrderFunctions.demo7
import appendix.HigherOrderFunctions.doubling
import appendix.HigherOrderFunctions.memo
import appendix.HigherOrderFunctions.single
import appendix.HigherOrderFunctions.celsius
import kotlin.test.*

class HigherOrderFunctionsTests {
  @Test
  fun testNegate() {
    assertTrue(Neg1.neg(-1))
    assertFalse(Neg1.neg(1))
    assertTrue(Neg2.neg(-1))
    assertFalse(Neg2.neg(1))
    assertTrue(Neg3.neg(-1))
    assertFalse(Neg3.neg(1))
  }

  @Test
  fun testByLength() {
    assertEquals(0, byLength.compare("foo", "bar"))
    assertTrue(byLength.compare("A", "AAA") < 0)
    assertTrue(byLength.compare("AAA", "A") > 0)
  }

  @Suppress("UNCHECKED_CAST")
  @Test
  fun testLen() {
    assertEquals(3, (Len1.len as ((String) -> Int))("foo"))
    assertEquals(3, Len2.len("foo"))
  }

  @Test
  fun testExistsOrEmpty() {
    assertTrue(e)
    assertTrue(existsOrEmpty(emptyList<Int>()) { false })
  }

  @Test
  fun testMap() {
    assertEquals(listOf(31, 33, 26, 21, 38, 37, 21), demo7())
  }

  @Test
  fun testFlatMap() {
    assertEquals(listOf(6, 0, 22, 38), celsius)
  }

  @Test
  fun testMemo() {
    var calls = 0
    val len = { str: String ->
      calls += 1
      str.length
    }
    val f = memo(len)
    assertEquals(3, f("foo"))
    assertEquals(1, calls)
    assertEquals(3, f("foo"))
    assertEquals(1, calls)
    assertEquals(3, f("bar"))
    assertEquals(2, calls)
    assertEquals(3, f("foo"))
    assertEquals(3, f("bar"))
    assertEquals(2, calls)
  }

  @Test
  fun testSingle() {
    val f = single { x: Int -> x + 1 }
    assertEquals(1, f(0))
    assertFailsWith<IllegalStateException> { f(1) }
  }

  @Test
  fun testDoubling() {
    assertEquals(200, doubling(100))
  }
}
