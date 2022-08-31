package appendix

import mocks.*
import kotlin.math.ceil

object Types {
  @JvmInline
  value class Length(val meters: Double)

  fun fromMeters(meters: Double): Length = Length(meters)
  fun wholeMeters(len: Length): Length = Length(ceil(len.meters))

  fun printTitles(pubs: List<Publication>) {
    for (pub in pubs) println(pub.title)
  }

  fun demo() {
    val books: List<Book> = listOf(book1, book2)
    printTitles(books) // prints both titles
  }

  fun printTitles(pubs: Array<out Publication>) {
    for (pub in pubs) println(pub.title)
  }
}
