package appendix

import mocks.print
import mocks.println

@Suppress("MemberVisibilityCanBePrivate")
object Functions {
  fun abs(x: Int): Int {
    fun max(a: Int, b: Int) = if (a > b) a else b
    return max(x, -x)
  }

  infix fun Int.max(that: Int): Int = if (this > that) this else that

  operator fun String.times(count: Int): String {
    val builder = StringBuilder(this.length * count)
    for (i in 1..count) builder.append(this)
    return builder.toString()
  }

  fun demo1(): String = "A" * 3 // "AAA"

  operator fun String.invoke(i: Int): Char = this[i]

  fun demo2(): Char = "foo"(2) // 'o'

  fun <A> firstLast(list: List<A>): List<A> = list.slice(listOf(0, list.size - 1))

  fun average(first: Double, vararg others: Double): Double =
    (first + others.sum()) / (1 + others.size)

  fun demo3(): Double = average(1.0, 2.3, 4.1)

  fun demo4(): Double = average(10.0, 20.0)

  fun write(str: String, withNewline: Boolean = true): Unit =
    if (withNewline) println(str) else print(str)

  fun demo5() = write("message")
  fun demo6() = write("message", false)
  fun demo7() = write("message", withNewline = false)
}
