package appendix

import appendix.Types.demo
import appendix.Types.fromMeters
import appendix.Types.printTitles
import appendix.Types.wholeMeters
import mocks.book1
import mocks.book2
import mocks.out
import kotlin.test.Test
import kotlin.test.assertEquals

class TypesTests {
  @Test
  fun testLength() {
    val len1 = fromMeters(1.23)
    val len2 = wholeMeters(len1)
    assertEquals(1.23, len1.meters)
    assertEquals(2.0, len2.meters)
  }

  @Test
  fun testPrintTitlesList() {
    out.clear()
    demo()
    assertEquals("A\nB\n", out.toString())
  }

  @Test
  fun testPrintTitlesArray() {
    val books = arrayOf(book1, book2)
    out.clear()
    printTitles(books)
    assertEquals("A\nB\n", out.toString())
  }
}
