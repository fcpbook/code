package mocks

import chap13.Section_1
import tinyscalautils.util.FastRandom

import java.nio.file.{ NoSuchFileException, Path }
import java.text.ParseException
import scala.util.{ Failure, Success, Try }

object Chap13:
   def search[A](values: List[A], target: A): Int = values.indexWhere(_ == target)

   def searchNull[A](values: List[A], target: A): Integer = search(values, target) match
      case -1    => null
      case index => index

   def searchOpt[A](values: List[A], target: A): Option[Int] =
      Some(search(values, target)).filter(_ >= 0)

   val wordFile: Path = Path.of("words.txt")
   val notFound: Path = Path.of("words")

   def readFile(file: Path): List[String] = file.toString match
      case "words.txt" => Section_1.words
      case "temps"     => List("Austin: 101", "Chicago: 88", "Big Spring: 92")
      case "bad_file"  => List("Austin: hot", "Chicago: 88", "Big", "Spring: 92")
      case other       => throw NoSuchFileException(other)

   def readFileTry(file: Path): Try[List[String]] = Try(readFile(file))

   def readFileEither(file: Path): Either[String, List[String]] =
      readFileTry(file).toEither.left.map("not found: " + _.getMessage)

   def compute(list: List[String]): Int = list.length

   def computeOrFail(list: List[String]): Try[Int] =
      if FastRandom.nextBoolean() then Success(list.length) else Failure(Exception())

   val states: Map[String, String] = Map("Austin" -> "TX", "Chicago" -> "IL", "Big Spring" -> "TX")

   def parse(line: String): (String, Int) =
      val parts = line.split(':')
      if parts.length < 2 then throw ParseException(line, parts(0).length)
      (parts(0).trim, parts(1).trim.toInt)

   def parseEither(line: String): Either[String, (String, Option[Int])] =
      val parts = line.split(':')
      if parts.length < 2 then Left(line)
      else Right((parts(0).trim, parts(1).trim.toIntOption))
