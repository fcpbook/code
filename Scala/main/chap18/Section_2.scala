package chap18

import chap12.Section_3.times
import tinyscalautils.text.{ println, threadTimeDemoMode }

import java.util.ArrayList

//noinspection ReferenceMustBePrefixed,DuplicatedCode
@main def Section_2(): Unit =
   println("START")

   // DON'T DO THIS!
   val shared = ArrayList[String]()

   def addStrings(n: Int, str: String): Unit = n times shared.add(str)

   val t1 = Thread(() => addStrings(5, "T1"), "T1")
   val t2 = Thread(() => addStrings(5, "T2"), "T2")

   t1.start(); t2.start()
   t1.join(); t2.join()

   println(shared.size)

   println("END")
