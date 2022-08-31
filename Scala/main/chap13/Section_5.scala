package chap13

import java.io.IOException
import java.nio.file.Path
import java.text.ParseException
import scala.util.Try

object Section_5:
   object NoFailureHandling:
      @throws[IOException]
      def readFile(file: Path): List[String] = mocks.Chap13.readFile(file)

      @throws[ParseException]
      @throws[NumberFormatException]
      def parse(line: String): (String, Int) = mocks.Chap13.parse(line)

      @throws[NoSuchElementException]
      def stateOf(city: String): String = mocks.Chap13.states(city)

      def convert(input: Path): List[String] =
         readFile(input).view
            .map(parse)
            .filter((city, _) => stateOf(city) == "TX")
            .map((city, temp) => (city, ((temp - 32) / 1.8f).round))
            .map((city, temp) => s"$city: $temp")
            .toList
   end NoFailureHandling

   def readFile(file: Path): Try[List[String]] = mocks.Chap13.readFileTry(file)

   def parse(line: String): Either[String, (String, Option[Int])] = mocks.Chap13.parseEither(line)

   def stateOf(city: String): Option[String] = mocks.Chap13.states.get(city)

   def convert(input: Path): Try[List[String]] =
      readFile(input).map { lines =>
         lines.view
            .map(parse)
            .filter(_.forall((city, _) => stateOf(city).contains("TX")))
            .map { badLineOrPair =>
               badLineOrPair
                  .map((city, temp) => (city, temp.map(t => ((t - 32) / 1.8f).round)))
                  .fold(
                    line => s"[$line]",
                    (city, temp) => s"""$city: ${temp.getOrElse("unknown")}"""
                  )
            }
            .toList
      }
