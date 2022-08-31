package chap19

import scala.compiletime.uninitialized

//noinspection ReferenceMustBePrefixed
object Section_5:
   class SafeStringList:
      private var contents = List.empty[String]

      def add(str: String): Unit = synchronized(contents ::= str)
      def size: Int              = synchronized(contents.size)
      def getAll: List[String]   = synchronized(contents)
   end SafeStringList

   object Variant1:
      class SafeStringList:
         private var contents: List[String] = uninitialized
         synchronized {
            contents = List.empty
         }

         def add(str: String): Unit = synchronized(contents ::= str)
         def size: Int              = synchronized(contents.size)
         def getAll: List[String]   = synchronized(contents)
      end SafeStringList
   end Variant1

   object Variant2:
      class SafeStringList:
         private var contents = List.empty[String]

         private val lock = Object()

         def add(str: String): Unit = lock.synchronized(contents ::= str)
         def size: Int              = lock.synchronized(contents.size)
         def getAll: List[String]   = lock.synchronized(contents)
      end SafeStringList
   end Variant2
