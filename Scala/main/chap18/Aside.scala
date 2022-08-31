package chap18

import tinyscalautils.text.{ println, threadTimeDemoMode }

import java.util.ArrayList

//noinspection ReferenceMustBePrefixed,DuplicatedCode
@main def Aside(): Unit =
   println("START")

   val shared = ArrayList[String]()

   def addString(n: Int, str: String): Unit =
      var added = 0
      while added < n do
         // println(added)
         if shared.size >= 2 * added then
            shared.add(str)
            added += 1

   val t1 = Thread(() => addString(5, "T1"), "T1")
   val t2 = Thread(() => addString(5, "T2"), "T2")

   t1.start(); t2.start()
   t1.join(); t2.join()

   println(shared.size)

   println("END")
