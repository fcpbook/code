package chap23

import mocks.Chap23.UserInfo

import java.nio.file.{ Files, Path }
import java.util.concurrent.locks.{ ReentrantLock, ReentrantReadWriteLock }
import scala.concurrent.duration.MILLISECONDS
import scala.util.Using

object Section_1:
   class Rank:
      private var userCount = 0
      private val lock      = ReentrantLock()

      def getRank(): Option[Int] =
         if lock.tryLock(100, MILLISECONDS) then
            try
               if userCount < 5 then
                  userCount += 1
                  Some(userCount)
               else None
            finally lock.unlock()
         else None
   end Rank

   class Users:

      import scala.collection.concurrent.TrieMap // thread-safe

      private val users = TrieMap.empty[String, UserInfo]

      private val (rlock, wlock) =
         val lock = ReentrantReadWriteLock()
         (lock.readLock, lock.writeLock)

      def register(username: String): Option[UserInfo] =
         val info = UserInfo(username)
         rlock.lock()
         try if users.putIfAbsent(username, info).isEmpty then Some(info) else None
         finally rlock.unlock()

      def saveToFile(filename: String): Unit =
         wlock.lock()
         try Using.resource(Files.newBufferedWriter(Path.of(filename))) { out =>
               for (user, info) <- users do out.write(s"$user:$info\n")
            }
         finally wlock.unlock()
   end Users
