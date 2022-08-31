package appendix

import mocks.Appendix.{ Socket, handleConnection }

import java.util.concurrent.ExecutorService
import scala.concurrent.{ ExecutionContext, Future }

object ThreadPools:
   def demo1(exec: ExecutorService, socket: Socket): Unit =
      exec.execute(() => handleConnection(socket))

   def demo2(exec: ExecutorService, socket: Socket): Unit =
      given ExecutionContext = ExecutionContext.fromExecutor(exec)

      Future {
         handleConnection(socket) // can use context implicitly to create futures
      }

   def demo3(socket: Socket): Unit =
      ExecutionContext.global.execute(() => handleConnection(socket))
end ThreadPools
