package chap25

import mocks.Chap25.*
import tinyscalautils.threads.Executors.global as exec

import scala.concurrent.duration.MILLISECONDS
import scala.concurrent.{ ExecutionException, TimeoutException }

object Section_3:
   def demo(request: Request): Ad =
      val futureAd = exec.submit(() => fetchAd(request))
      // DB lookup and logging
      val ad =
         try futureAd.get(500, MILLISECONDS)
         catch
            case _: ExecutionException => failedAd
            case _: TimeoutException   => futureAd.cancel(true); timeoutAd

      ad
   end demo
