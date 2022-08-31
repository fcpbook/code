package chap23

import chap12.Section_3.times
import mocks.Chap23.{ N, capacity }

import java.io.Writer
import java.nio.file.Path
import java.util.concurrent.{ ArrayBlockingQueue, ConcurrentLinkedQueue, ExecutorService }
import scala.io.Codec.UTF8
import scala.io.Source
import scala.util.Using

//noinspection DuplicatedCode
object Section_5:
   def demo1(someExecutor: ExecutorService): Unit =
      val exec: ExecutorService              = someExecutor
      def isMatch(line: String): Boolean     = mocks.Chap23.isMatch(line)
      val files: ConcurrentLinkedQueue[Path] = mocks.Chap23.files
      val out: Writer                        = mocks.Chap23.outWriter

      def searchFile(path: Path): Unit =
         Using.resource(Source.fromFile(path.toFile)(UTF8)) { in =>
            for line <- in.getLines() do
               if isMatch(line) then out.synchronized { out.write(line); out.write('\n') }
         }

      val searchTask: Runnable = () =>
         var file = Option(files.poll())
         while file.nonEmpty do
            searchFile(file.get)
            file = Option(files.poll())

      N times exec.execute(searchTask)
   end demo1

   def demo2(someExecutor: ExecutorService): Unit =
      val exec                               = someExecutor
      def isMatch(line: String): Boolean     = mocks.Chap23.isMatch(line)
      val files: ConcurrentLinkedQueue[Path] = mocks.Chap23.files
      val out: Writer                        = mocks.Chap23.outWriter

      val queue = ArrayBlockingQueue[Option[String]](capacity)

      def searchFile(path: Path): Unit =
         Using.resource(Source.fromFile(path.toFile)(UTF8)) { in =>
            for line <- in.getLines() do if isMatch(line) then queue.put(Some(line))
         }

      val searchTask: Runnable = () =>
         var file = Option(files.poll())
         while file.nonEmpty do
            searchFile(file.get)
            file = Option(files.poll())
         queue.put(None)

      val writeTask: Runnable = () =>
         var active = N
         while active > 0 || !queue.isEmpty do
            queue.take() match
               case None => active -= 1
               case Some(line) =>
                  out.write(line)
                  out.write('\n')

      N times exec.execute(searchTask)
      exec.execute(writeTask)
   end demo2
