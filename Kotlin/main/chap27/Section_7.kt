package chap27

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

val strings = Channel<String>()
val ints = Channel<Int>()

suspend fun task1() = coroutineScope {
  launch { task2() }
  println("waiting for a string")
  val str = strings.receive()
  ints.send(str.length)
}

suspend fun task2() = coroutineScope {
  println("waiting for an int")
  val n = ints.receive()
  strings.send(n.toString())
}

fun main() = runBlocking(Dispatchers.Default) {
  println("START")
  launch { task1() }
  println("END")
}
