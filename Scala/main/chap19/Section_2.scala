package chap19

import chap12.Section_3.times

import java.util.ArrayList

//noinspection TypeAnnotation
object Section_2:
   class Demo:
      class SafeStringList:
         private val contents = ArrayList[String]()

         def add(str: String): Unit = synchronized(contents.add(str))
         def size: Int              = synchronized(contents.size)
      end SafeStringList

      val shared = SafeStringList()

      def addStrings(n: Int, str: String): Unit = n times shared.add(str)
   end Demo
