package mocks

import tinyscalautils.timing.sleep

import java.net.{ Socket, URL }
import java.util.concurrent as juc
import java.util.concurrent.ExecutorService
import java.util.logging.Logger
import scala.io.Source
import scala.util.Using

object Chap21:
   val port           = 0
   var N              = 8
   val logger: Logger = Logger.getGlobal

   val serverLines: juc.BlockingQueue[String] = juc.LinkedBlockingQueue()

   def handleConnection(socket: Socket): Unit =
      sleep(1.0)
      Using.resource(Source.fromInputStream(socket.getInputStream)) { in =>
         for line <- in.getLines() do serverLines.put(line)
      }

   val urls: List[URL] = List(
     "https://gutenberg.org/files/13951/13951-0.txt",
     "https://gutenberg.org/files/2650/2650-0.txt",
     "https://gutenberg.org/files/98/98-0.txt",
     "https://gutenberg.org/files/1342/1342-0.txt",
     "https://gutenberg.org/files/76/76-0.txt",
     "https://gutenberg.org/files/4300/4300-0.txt",
     "https://gutenberg.org/files/28054/28054-0.txt",
     "https://gutenberg.org/files/6130/6130-0.txt",
     "https://gutenberg.org/files/2000/2000-0.txt",
     "https://gutenberg.org/files/1012/1012-0.txt"
   ).map(URL(_))

   def distinctWordsCount(url: URL): Int = chap21.Section_4.Demo1.distinctWordsCount(url)
