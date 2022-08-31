package appendix

import mocks.temps
import kotlin.math.roundToInt

@Suppress("MemberVisibilityCanBePrivate")
object HigherOrderFunctions {
  fun <A> negate(f: (A) -> Boolean): (A) -> Boolean = { x -> !f(x) }

  fun pos(x: Int): Boolean = x > 0

  object Neg1 {
    val neg: (Int) -> Boolean = negate { x -> pos(x) }
  }

  object Neg2 {
    val neg: (Int) -> Boolean = negate { pos(it) }
  }

  object Neg3 {
    val neg: (Int) -> Boolean = negate(::pos)
  }

  //val byLength: Comparator<String> = { a, b -> a.length.compareTo(b.length) }
  val byLength = Comparator<String> { a, b -> a.length.compareTo(b.length) }

  object Len1 {
    val len: Any = { str: String -> str.length } // OK
  }

  object Len2 {
    val len = { str: String -> str.length } // type (String) -> Int inferred
  }

  fun <A> existsOrEmpty(list: List<A>, test: (A) -> Boolean): Boolean =
    list.isEmpty() || list.any(test)

  val e = existsOrEmpty(listOf(1, 2, 3)) { num ->
    num > 1
  }

  fun demo7() = temps.map { temp -> ((temp - 32) / 1.8f).roundToInt() }

  val SPACES = Regex("\\s+")
  val strings: List<String> = mocks.strings

  val celsius: List<Int> = strings
    .flatMap { SPACES.split(it) }
    .mapNotNull { it.toIntOrNull() }
    .map { ((it - 32) / 1.8f).roundToInt() }

  fun <A, B> memo(f: (A) -> B): (A) -> B {
    val store = mutableMapOf<A, B>()
    return { x -> store.computeIfAbsent(x, f) }
  }

  fun <A, B> single(f: (A) -> B): (A) -> B {
    var called = false
    return { x ->
      if (called) throw IllegalStateException()
      called = true
      f(x)
    }
  }

  val doubling: (Int) -> Int = { it + it }
}
