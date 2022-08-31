package chap13

import chap13.Section_2.between
import mocks.Chap13.{ notFound, wordFile }
import tinyscalautils.threads.Executors.global

import java.nio.file.Path
import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

object Section_3:
   def readFile(file: Path): Try[List[String]] = mocks.Chap13.readFileTry(file)

   def demo1: Try[List[String]] = readFile(wordFile).map(between(_, "two", "three")) // Success(List("two", "three"))
   def demo2: Try[List[String]] = readFile(notFound).map(between(_, "two", "three")) // Failure(...)

   def compute(list: List[String]): Int            = mocks.Chap13.compute(list)
   def computeOrFail(list: List[String]): Try[Int] = mocks.Chap13.computeOrFail(list)

   def demo3(file: Path): Try[Int] = readFile(file).map(compute)
   def demo4(file: Path): Try[Int] = readFile(file).flatMap(computeOrFail)

   def demo5(file: Path): Option[List[String]] = readFile(file).toOption // of type Option[List[String]]

   def demo6(future: Future[Int], use: Int => Unit, handle: Throwable => Unit): Unit =
      future.onComplete {
         case Success(value) => use(value)    // use value
         case Failure(error) => handle(error) // handle error
      }
