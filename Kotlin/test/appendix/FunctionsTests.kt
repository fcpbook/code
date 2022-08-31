package appendix

import appendix.Functions.abs
import appendix.Functions.average
import appendix.Functions.demo1
import appendix.Functions.demo2
import appendix.Functions.demo3
import appendix.Functions.demo4
import appendix.Functions.demo5
import appendix.Functions.demo6
import appendix.Functions.demo7
import appendix.Functions.firstLast
import appendix.Functions.invoke
import appendix.Functions.max
import appendix.Functions.times
import mocks.out
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FunctionsTests {
  @Test
  fun testAbs() {
    assertEquals(42, abs(42))
    assertEquals(42, abs(-42))
  }

  @Test
  fun testMax() {
    assertEquals(42, 42 max 10)
    assertEquals(42, 42.max(10))
  }

  @Test
  fun testTimes() {
    assertEquals("AAA", demo1())
    assertTrue(("A" * 0).isEmpty())
  }

  @Test
  fun testInvoke() {
    assertEquals('o', demo2())
    assertFailsWith<StringIndexOutOfBoundsException> { "foo"(3) }
  }

  @Test
  fun testFirstLast() {
    assertEquals(listOf("A", "C"), firstLast(listOf("A", "B", "C")))
  }

  @Test
  fun testAverage() {
    assertEquals((1.0 + 2.3 + 4.1) / 3.0, demo3())
    assertEquals(15.0, demo4())
    assertEquals(10.0, average(10.0))
  }

  @Test
  fun testWrite() {
    out.clear()
    demo5()
    assertEquals("message\n", out.toString())
    out.clear()
    demo6()
    assertEquals("message", out.toString())
    out.clear()
    demo7()
    assertEquals("message", out.toString())
  }
}
