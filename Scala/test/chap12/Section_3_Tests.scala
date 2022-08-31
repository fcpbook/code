package chap12

import chap12.Section_3.*
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout
import tinyscalautils.timing.sleep

import java.io.ObjectInputStream
import java.lang.AutoCloseable
import java.nio.file.{ Files, Path }
import scala.jdk.CollectionConverters.IterableHasAsJava
import scala.util.Using

class Section_3_Tests extends AnyFunSuite with Tolerance:
   test("demos") {
      assert(printout(demo1()) == "Beetlejuice!\nBeetlejuice!\nBeetlejuice!\n")
      assert(printout(demo2()) == "Beetlejuice!\nBeetlejuice!\nBeetlejuice!\n")
   }

   test("writeToFile") {
      def testWriteToFile(writeToFile: (Path, Iterable[String]) => Unit) =
         val tmp = Files.createTempFile("foo", ".tmp")
         tmp.toFile.deleteOnExit()

         val values = List("foo", "bar")
         writeToFile(tmp, values)

         Using.resource(ObjectInputStream(Files.newInputStream(tmp))) { in =>
            assert(in.readObject() == "foo")
            assert(in.readObject() == "bar")
         }
      end testWriteToFile

      testWriteToFile(writeToFile)
      testWriteToFile((path, values) => Section_3J.writeToFile(path, values.asJava))
   }

   test("resource") {
      object C extends AutoCloseable:
         var isClosed = false

         def close() = isClosed = true
      assert(!resource(C)(_.isClosed))
      assert(C.isClosed)
   }

   test("timeOf") {
      assert(timeOf(sleep(1.0)) === 1.0 +- 0.1)
   }
