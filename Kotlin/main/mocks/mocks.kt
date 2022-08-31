package mocks

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.random.Random
import kotlin.random.nextInt

typealias Request = Int
typealias Ad = Int
typealias Data = Int
typealias Page = Int
typealias Socket = CountDownLatch

val out: StringBuilder = StringBuilder()

fun print(any: Any): Unit = synchronized(out) {
  out.append(any)
}

fun println(any: Any): Unit = synchronized(out) {
  out.append(any).append('\n')
}

fun fetchAd(request: Request): Ad {
  SECONDS.sleep(3L)
  return request + 3
}

fun dbLookup(request: Request): Data {
  SECONDS.sleep(2L)
  return request + 2
}

fun makePage(data: Data, ad: Ad): Page {
  return data + ad
}

val temps = listOf(88, 91, 78, 69, 100, 98, 70)
val strings: List<String> = listOf("42  32", "<no data> 72 100")

fun computeSomething() = SECONDS.sleep(1)

var computed = 0

fun someComputation(): Int {
  computed += 1
  return computed
}

val ex = Exception()

fun stringsOrFail(): List<String> = if (Random.nextBoolean()) listOf("A") else throw ex

fun someListComputation(): List<String> = listOf("B")

fun compute(list: List<String>): Int = list.size

fun computeOrFail(list: List<String>): Result<Int> = runCatching {
  if (Random.nextBoolean()) list.size else throw ex
}

fun maybeString(): String? = when (Random.nextInt(1..3)) {
  1 -> "a"
  2 -> "42"
  else -> null
}

fun someStringComputation() = "B"

fun maybeOtherString(): String? = if (Random.nextBoolean()) "B" else null

interface Publication {
  val title: String
}

data class Book(override val title: String) : Publication

val book1: Book = Book("A")
val book2: Book = Book("B")

fun handleConnection(socket: Socket) {
  println(Thread.currentThread().name)
  socket.countDown()
}
