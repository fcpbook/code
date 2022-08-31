package mocks

import chap10.Section_8.{ BinTree, Empty, Node }
import tinyscalautils.text.StringLetters.*

import java.io.*
import java.util.stream.IntStream
import scala.annotation.unused
import scala.jdk.StreamConverters.IterableHasSeqStream

object Chap10:
   def temps: IntStream = chap10.Section_1.temps.asJavaSeqStream

   private var currentOut = Option.empty[ByteArrayOutputStream]

   def outBytes: Option[Array[Byte]] = currentOut.map(_.toByteArray)

   def someOut(): DataOutputStream =
      val buffer = ByteArrayOutputStream()
      currentOut = Some(buffer)
      DataOutputStream(buffer)

   var currentWriter: Option[StringWriter] = Option.empty

   def writerString: Option[String] = currentWriter.map(_.toString)

   def someWriter(): Writer =
      currentWriter = Some(StringWriter())
      currentWriter.get

   type Request   = Int
   type User      = Int
   type Operation = Int
   type Account   = Int

   def parseRequest(@unused request: Request): User = 0

   def getAccount(user: User): Account = user - 1

   def applyOperation(@unused account: Account, @unused op: Operation): Int = 0

   def parseRequestOpt(request: Request): Option[User] =
      if request > 0 then Some(request - 1) else None

   def getAccountOpt(user: User): Option[Account] = if user > 0 then Some(user - 1) else None

   def applyOperationOpt(account: Account, @unused op: Operation): Option[Int] =
      if account > 0 then Some(account - 1) else None

   val struct: List[Int] = List(1, 2, 3)

   object Functor:
      def f(n: Int): String = n.toString

      def g(s: String): Int = s.length

   object Monad:
      def unit[A](x: A): List[A] = List(x)

      def f(n: Int): List[String] = List.fill(n)(n.toString)

      def g(s: String): List[Int] = s.toList.map(_.toInt)

      def x = 42

   val abc: List[String] = List(A, B, C)

   def f1(x: Any): String = s"f($x)"

   def f2(x: Any, y: Any): String = s"f($x,$y)"

   val strings: List[String]          = List("X", "ABC", "DEF", "Y")
   val points: List[(Double, Double)] = List((3.2, 1.1), (2.3, 0.8))

   val tree: BinTree =
      Node(3, Node(2, Node(1, Empty, Empty), Empty), Node(5, Node(4, Empty, Empty), Empty))
