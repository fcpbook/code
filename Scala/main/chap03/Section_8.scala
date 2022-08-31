package chap03

import scala.collection.mutable

//noinspection TypeAnnotation
object Section_8:
   object MutableDirectory:
      type Directory = mutable.Set[String]

      def register(dir: Directory, user: String): Unit = dir += user

      def newRegistrations(newDir: Directory, oldDir: Directory): Directory =
         newDir.subtractAll(oldDir)

      object Demo1:
         val yesterdayDir: Directory = mocks.Chap03.someOldMutableDirectory
         val todayDir: Directory     = mocks.Chap03.someMutableDirectory
         register(todayDir, "new user 1")
         register(todayDir, "new user 2")
         val todayReg = newRegistrations(todayDir, yesterdayDir)

      object Demo2:
         val yesterdayDir: Directory = mocks.Chap03.someOldMutableDirectory
         val todayDir: Directory     = mocks.Chap03.someMutableDirectory
         register(todayDir, "new user 1")
         register(todayDir, "new user 2")
         val todayReg = newRegistrations(todayDir.clone(), yesterdayDir.clone())
   end MutableDirectory

   object ImmutableDirectory:
      type Directory = Set[String]

      def register(dir: Directory, user: String): Directory = dir + user

      def newRegistrations(newDir: Directory, oldDir: Directory): Directory = newDir.diff(oldDir)

      object Demo:
         val yesterdayDir: Directory = mocks.Chap03.someOldImmutableDirectory
         var todayDir: Directory     = mocks.Chap03.someImmutableDirectory
         todayDir = register(todayDir, "new user 1")
         todayDir = register(todayDir, "new user 2")
         val todayReg = newRegistrations(todayDir, yesterdayDir)
   end ImmutableDirectory
