package chap09

import mocks.Chap09.logger

import java.util.Comparator
import java.util.stream.IntStream

object Section_5:
   abstract class Formatter:
      def format(str: String): String
      def println(any: Any): Unit = Predef.println(format(any.toString))

   def demo(someValue: Any): Unit =
      val f: Formatter = str => str.toUpperCase
      f.println(someValue)

   val absComp: Comparator[Int] = (x, y) => x.abs.compareTo(y.abs)
   val stream: IntStream        = mocks.Chap09.someStream

   val loggingStream: IntStream = stream.onClose(() => logger.info("closing stream"))
