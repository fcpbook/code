package preface

import mocks.Preface.timeout

import scala.concurrent.{ Await, ExecutionContext, Future }

object FirstOf:
   def firstOf(comp1: StringComputation, comp2: StringComputation)(using ExecutionContext): String = {
      val future1 = Future(comp1.compute())
      val future2 = Future(comp2.compute())
      Await.result(Future.firstCompletedOf(Set(future1, future2)), timeout)
   }
