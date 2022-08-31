package mocks

import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.logging.{ Logger, SimpleFormatter, StreamHandler }
import java.util.stream.IntStream

object Chap09:
   case class Project(id: Long)

   val projects: List[Project] = List(Project(11111L), Project(12345L))

   val date1: LocalDate = LocalDate.of(2022, 3, 14) // Pi day
   val date2: LocalDate = LocalDate.of(2022, 5, 4)  // Star Wars day
   val date3: LocalDate = LocalDate.of(2022, 7, 4)  // 4th of July

   val datedTemps: List[(LocalDate, Int)] = List((date1, 89), (date2, 93), (date1, 93), (date3, 88))
   val optionalDatedTemps: List[(Option[LocalDate], Int)] =
      List((None, 93), (Some(date2), 92), (Some(date1), 91))

   val celsiusTemps: List[Int] = List(31, 33, 26, 21, 38, 37, 21)

   val someStream: IntStream = IntStream.of(1, 2, 3)

   private val loggerBuffer: ByteArrayOutputStream = ByteArrayOutputStream()
   private val loggerHandler                       = StreamHandler(loggerBuffer, SimpleFormatter())

   def startLogging(): Unit = loggerBuffer.reset()

   def endLogging(): String =
      loggerHandler.flush()
      loggerBuffer.toString()

   val logger: Logger =
      val theLogger = Logger.getLogger("my.package")
      theLogger.setUseParentHandlers(false)
      theLogger.addHandler(loggerHandler)
      theLogger

   given chap09.Section_5.Formatter = str => str
