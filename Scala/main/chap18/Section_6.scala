package chap18

import chap12.Section_3.times
import mocks.Chap18.{ X, Y }

import java.util.ArrayList
import scala.annotation.unused

//noinspection TypeAnnotation,ReferenceMustBePrefixed,DuplicatedCode
object Section_6:
   @unused object SharedListApplication:
      @main def run(msg: String, count: Int) =
         val msg1 = msg + "1"
         val msg2 = msg + "2"

         val shared = ArrayList[String]()

         // possible choices of locks:
         val X = shared
         // val X = SharedListApplication
         // val X = msg1
         // val X = Nil

         class Adder(str: String):
            def addStrings(n: Int): Unit =
               n times {
                  X.synchronized(shared.add(str))
                  // this.synchronized(shared.add(str)) // DON'T DO THIS!
               }

         val t1 = Thread(() => Adder(msg1).addStrings(count), "T1")
         val t2 = Thread(() => Adder(msg2).addStrings(count), "T2")

         t1.start(); t2.start()
         t1.join(); t2.join()

         println(shared.size)
      end run
   end SharedListApplication

   class Demo1:
      private var userCount = 0
      private val lock      = Object()

      def getRank(): Option[Int] = lock.synchronized {
         if userCount < 5 then
            userCount += 1
            Some(userCount)
         else None
      }
   end Demo1

   object Demo2:
      def getRank(): Option[Int] = X.synchronized {
         mocks.Chap18.getRank() // register user
      }

      def play(rank: Int): Unit = Y.synchronized {
         mocks.Chap18.play(rank) // act in the game
      }
   end Demo2
