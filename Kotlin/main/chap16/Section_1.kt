package chap16

import kotlinx.coroutines.*
import mocks.println

fun asyncABC() = runBlocking {
  withContext(Dispatchers.Default) {
    println('A')
    launch { println('B') }
    println('C')
  }
}

fun joinABC() = runBlocking {
  withContext(Dispatchers.Default) {
    println('A')
    launch { println('B') }.join()
    println('C')
  }
}
