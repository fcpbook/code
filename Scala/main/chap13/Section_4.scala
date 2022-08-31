package chap13

import chap13.Section_2.between
import mocks.Chap13.{ notFound, wordFile }

import java.nio.file.Path

object Section_4:
   def readFile(file: Path): Either[String, List[String]] = mocks.Chap13.readFileEither(file)

   def demo1: Either[String, List[String]] = readFile(wordFile).map(between(_, "two", "three")) // Right(List("two", "three"))
   def demo2: Either[String, List[String]] = readFile(notFound).map(between(_, "two", "three")) // Left("not found: words")

   def demo3: Either[String, Int] = Some(42).toRight("no number") // Right(42)
   def demo4: Either[String, Int] = None.toRight("no number")     // Left("no number")

   def mkString(stringOrNumber: Either[String, Int]): String =
      stringOrNumber.fold(identity, n => if n < 0 then s"($n)" else s"$n")

   def demo5: String = mkString(Right(-42))        // "(-42)"
   def demo6: String = mkString(Left("no number")) // "no number"
