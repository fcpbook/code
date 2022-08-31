package appendix

import mocks.someListComputation

@Suppress("MemberVisibilityCanBePrivate")
object HandlingFailures {
  val lines: Result<List<String>> = runCatching {
    mocks.stringsOrFail() // a list-producing computation
  }

  val linesOrNull: List<String>? = lines.getOrNull()
  val linesOrEmpty: List<String> = lines.getOrDefault(listOf())
  val linesOrOther: List<String> = lines.getOrElse { someListComputation() }

  fun compute(list: List<String>): Int = mocks.compute(list)
  fun computeOrFail(list: List<String>): Result<Int> = mocks.computeOrFail(list)

  fun demo1(): Result<Int> = lines.map { compute(it) }
  fun demo2(): Result<Int> = lines.mapCatching { computeOrFail(it).getOrThrow() }

  object SafeCall {
    val maybeString: String? = mocks.maybeString()

    val optStr: String? = maybeString?.uppercase()
    val optInt: Int? = maybeString?.toIntOrNull()
  }

  object Elvis {
    fun someStringComputation(): String = mocks.someStringComputation()
    val maybeString: String? = mocks.maybeString()
    fun maybeOtherString(): String? = mocks.maybeOtherString()

    val str: String = maybeString ?: someStringComputation()
    val optStr: String? = maybeString ?: maybeOtherString()
  }
}
