package mocks

import java.util.concurrent.Semaphore

object Chap19:
   val semaphore: Semaphore   = Semaphore(0)
   private val task: Runnable = () => semaphore.release()
   val tasksA: List[Runnable] = List(task, task)
   val tasksB: List[Runnable] = List(task)

   // noinspection TypeAnnotation
   object SafeStringList:
      val demo = chap19.Section_3.Demo()
      type SafeStringList = demo.SafeStringList
      def apply(): SafeStringList = new demo.SafeStringList()
