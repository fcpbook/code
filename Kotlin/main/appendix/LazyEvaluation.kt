package appendix

import mocks.computeSomething
import mocks.someComputation
import java.math.BigInteger
import kotlin.random.Random

@Suppress("MemberVisibilityCanBePrivate")
object LazyEvaluation {
  fun demo1() = List(5) { i -> "X".repeat(i + 1) }

  fun demo2() = List(5) { Random.nextInt(1, 11) }

  @Suppress("RedundantLambdaArrow")
  fun demo3() = List(5) { _ -> Random.nextInt(1, 11) }

  fun <U> timeOf(code: () -> U): Double {
    val start = System.nanoTime()
    code()
    val end = System.nanoTime()
    return (end - start) / 1E9
  }

  val time = timeOf {
    computeSomething()
  }

  fun collatz(start: BigInteger): Int =
    generateSequence(start) { n ->
      if (n % BigInteger.TWO == BigInteger.ZERO) n / BigInteger.TWO
      else n * 3.toBigInteger() + BigInteger.ONE
    }
      .takeWhile { n -> n != BigInteger.ONE }
      .count()

  /* Note that collatz would be more idiomatic in Kotlin as:
  fun collatz(start: BigInteger): Int =
    generateSequence(start) { n ->
      when {
        n == BigInteger.ONE -> null
        (n % BigInteger.TWO) == BigInteger.ZERO -> n / BigInteger.TWO
        else -> n * 3.toBigInteger() + BigInteger.ONE
      }
    }.count()
  */

  val variable: Int by lazy {
    someComputation()
  }
}
