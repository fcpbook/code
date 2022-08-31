package chap22

import chap22.Section_3.Deadlock.SafeBox

import java.util.concurrent.Executors

object Section_4:
   @main def TestDeadlock(): Unit = // might deadlock
      val exec = Executors.newCachedThreadPool()
      val box  = SafeBox[Int]()
      exec.execute(() => box.set(0))
      println(box.get)
      exec.shutdown()
