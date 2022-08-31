package mocks

import tinyscalautils.util.{ FastRandom, nextInt }

import java.io.{ StringWriter, Writer }
import java.nio.file.{ Files, Path }
import java.util.concurrent.{ ConcurrentLinkedQueue, CyclicBarrier }
import scala.concurrent.ExecutionContextExecutorService
import scala.jdk.CollectionConverters.IterableHasAsJava

object Chap23:
   case class UserInfo(name: String)

   val fileCount: Int = FastRandom.nextInt(1 to 100)
   val N: Int         = FastRandom.nextInt(1 to 2 * Runtime.getRuntime.availableProcessors())
   val capacity: Int  = FastRandom.nextInt(1 to 32)

   private val paths = Seq.tabulate(fileCount) { i0 =>
      val i    = i0 + 1
      val file = Files.createTempFile(s"file-$i-", ".txt")
      file.toFile.deleteOnExit()
      Files.writeString(file, s"foo\nMATCH ${i}A\nMATCH ${i}B\nbar\n")
      file
   }

   val matches: Set[String] =
      for
         i <- Set.range(1, fileCount + 1)
         line = s"MATCH $i"
         str <- Seq(line + "A", line + "B")
      yield str

   def files: ConcurrentLinkedQueue[Path] = ConcurrentLinkedQueue[Path](paths.asJavaCollection)

   private val stringWriter = StringWriter()

   def outWriter: Writer = stringWriter

   def outWritten: String =
      val str = stringWriter.getBuffer.toString
      stringWriter.getBuffer.setLength(0)
      str

   def isMatch(line: String): Boolean = line.contains("MATCH")
