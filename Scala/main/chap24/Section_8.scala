package chap24

import java.util.concurrent.Executor
import java.util.concurrent.locks.ReentrantLock

//noinspection DuplicatedCode
object Section_8:
   object SingleCondition:
      class Runner[A](exec: Executor)(comp: A => Unit):
         private var active = 0
         private var runs   = 0

         def run(inputs: Seq[A]): Unit = synchronized {
            while active != 0 do wait()
            val myRun = runs + 1
            runs = myRun
            active = inputs.length
            for input <- inputs do exec.execute(task(input))
            while runs == myRun && active != 0 do wait()
         }

         // noinspection ScalaUnusedSymbol
         // DON'T DO THIS!
         def run_bad1(inputs: Seq[A]): Unit = synchronized {
            while active != 0 do wait()
            active = inputs.length
            for input <- inputs do exec.execute(task(input))
            while active != 0 do wait()
         }

         // noinspection ScalaUnusedSymbol
         // DON'T DO THIS!
         def run_bad2(inputs: Seq[A]): Unit = synchronized {
            while active != 0 do wait()
            active = inputs.length
            for input <- inputs do exec.execute(task(input))
            wait()
         }

         def addInput(input: A): Boolean = synchronized {
            if active == 0 then false
            else
               active += 1
               exec.execute(task(input))
               true
         }

         private def task(in: A): Runnable = () =>
            try comp(in)
            finally synchronized {
                  active -= 1
                  if active == 0 then notifyAll()
               }
      end Runner
   end SingleCondition

   object TwoConditions:
      class Runner[A](exec: Executor)(comp: A => Unit):
         private var active        = 0
         private var runs          = 0
         private val lock          = ReentrantLock()
         private val start, finish = lock.newCondition()

         def run(inputs: Seq[A]): Unit =
            lock.lock()
            try
               while active != 0 do start.await()
               val myRun = runs + 1
               runs = myRun
               active = inputs.length
               for input <- inputs do exec.execute(task(input))
               while runs == myRun && active != 0 do finish.await()
               start.signal()
            finally lock.unlock()

         def addInput(input: A): Boolean =
            lock.lock()
            try
               if active == 0 then false
               else
                  active += 1
                  exec.execute(task(input))
                  true
            finally lock.unlock()

         private def task(in: A): Runnable = () =>
            try comp(in)
            finally
               lock.lock()
               try
                  active -= 1
                  if active == 0 then finish.signal()
               finally lock.unlock()
      end Runner
