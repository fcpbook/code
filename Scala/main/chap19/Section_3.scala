package chap19

import chap12.Section_3.times

import java.util.{ ArrayList, Collections, List }

// noinspection ReferenceMustBePrefixed,TypeAnnotation, ScalaUnusedSymbol
object Section_3:
   class Demo:
      class SafeStringList:
         private val contents = ArrayList[String]()

         def add(str: String): Unit = synchronized(contents.add(str))
         def size: Int              = synchronized(contents.size)

         // DON'T DO THIS!
         def getAll_bad1: List[String] = synchronized(contents)

         // DON'T DO THIS!
         def getAll_bad2: List[String] = Collections.unmodifiableList(synchronized(contents))

         def getAll: List[String] = ArrayList(synchronized(contents))
      end SafeStringList

      val shared = SafeStringList()

      def addStrings(n: Int, str: String): Unit = n times shared.add(str)
   end Demo
