package chap19

import mocks.Chap19.SafeStringList.SafeStringList

import java.util.ArrayList

//noinspection ReferenceMustBePrefixed
object Section_4:
   class Demo1:
      val shared: SafeStringList = mocks.Chap19.SafeStringList()

      def addStringIfCapacity(str: String, bound: Int): Boolean = shared.synchronized {
         if shared.size >= bound then false
         else
            shared.add(str)
            true
      }

   object Demo2:
      class SafeStringList:
         private val contents = ArrayList[String]()

         def add(str: String): Unit = contents.synchronized(contents.add(str))
         def size: Int              = contents.synchronized(contents.size)
      end SafeStringList
