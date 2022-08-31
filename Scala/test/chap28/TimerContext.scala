package chap28

import java.util.concurrent.{ RejectedExecutionException, ScheduledThreadPoolExecutor }
import scala.concurrent.ExecutionContext

class TimerContext(threads: Int) extends ScheduledThreadPoolExecutor(threads) with ExecutionContext:
   def reportFailure(cause: Throwable): Unit =
      if !cause.isInstanceOf[RejectedExecutionException] then ExecutionContext.defaultReporter(cause)
