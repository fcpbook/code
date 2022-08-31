package appendix

import kotlinx.coroutines.*
import mocks.handleConnection
import mocks.Socket
import java.util.concurrent.ExecutorService

object ThreadPools {
  fun demo1(exec: ExecutorService, socket: Socket) = exec.execute {
    handleConnection(socket)
  }

  fun demo2(exec: ExecutorService, socket: Socket) = runBlocking {
    val dispatcher = exec.asCoroutineDispatcher()
    withContext(dispatcher) {
      // can use context implicitly to run coroutines
      handleConnection(socket)
    }
  }

  fun demo3(socket: Socket) {
    Dispatchers.Default.asExecutor().execute {
      handleConnection(socket)
    }
  }
}
