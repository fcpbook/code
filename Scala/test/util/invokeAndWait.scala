package util

import tinyscalautils.test.text.PrintAccumulator
import tinyscalautils.text.{ PrintingMode, threadMode }
import tinyscalautils.threads.newThread

def invokeAndWait(code: PrintingMode ?=> Any, waitForChildren: Boolean): IndexedSeq[String] =
   given out: PrintAccumulator = PrintAccumulator(threadMode)
   newThread(name = "main", waitForChildren = waitForChildren)(code).join()
   out.resetLines().map(_.stripLineEnd)
