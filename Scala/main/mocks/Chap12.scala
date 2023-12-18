package mocks

import tinyscalautils.assertions.requireState

import java.net.InetAddress
import java.nio.file.Path
import java.util.logging.Logger

object Chap12:
   val ip: InetAddress = InetAddress.getLocalHost

   case class Data(value: Int)

   val logger: Logger = Logger.getLogger("my.package")

   def read(file: Path): List[String] =
      println(s"reading $file")
      List.tabulate(5)(i => file.toString + (i + 1))

   def times10(n: Int): Int =
      println("multiplying")
      n * 10

   class ExpensiveType

   object ExpensiveType:
      var canCreate = false
      def create(): ExpensiveType =
         requireState(canCreate)
         ExpensiveType()
