package chap12

import chap12.Section_8.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.text.StringLetters.{ L, M, R }
import tinyscalautils.text.printout
import tinyscalautils.util.FastRandom

import java.nio.file.Path

class Section_8_Tests extends AnyFunSuite:
   private val largeNum = BigInt("992774507105260663893249807781832616822016143650134730933270")
   private val hugeNum  = BigInt(1 << 18, FastRandom(0))
   private val files    = List("A", "B", "C").map(Path.of(_))

   test("demos") {
      assert(demo1 == 111)
      assert(demo2 == 2632)
   }

   test("collatz 27") {
      assert(WithLoops.collatz(27) == 111)
      assert(WithStreams.collatz(27) == 111)
      assert(collatz(27) == 111)
   }

   test("collatz large") {
      assert(WithLoops.collatz(largeNum) == 2632)
      assert(WithStreams.collatz(largeNum) == 2632)
      assert(collatz(largeNum) == 2632)
   }

   test("collatz huge with loops") {
      assert(WithLoops.collatz(hugeNum) == 1901315)
   }

   test("collatz huge with iterators") {
      assert(collatz(hugeNum) == 1901315)
   }

   test("hanoi") {
      assert(hanoiIterator(3, L, M, R).toList == hanoi3)
      assert(hanoiIterator(20, L, M, R).length == (1 << 20) - 1)
      val i = moves
      49 times i.next()
      assert(i.next() == ('L', 'R'))
      (999 - 49 - 1) times i.next()
      assert(i.next() == ('M', 'L'))
   }

   test("searchFiles") {
      def testSearch(search: (List[Path], String => Boolean) => Option[String]) =
         var found = Option.empty[String]
         val str = printout {
            found = search(files, _ == "B3")
         }
         assert(found.contains("B3"))
         str

      assert(testSearch(Variant1.searchFiles) == "reading A\nreading B\n")
      assert(testSearch(Variant2.searchFiles) == "reading A\nreading B\nreading C\n")
      assert(testSearch(searchFiles) == "reading A\nreading B\n")
   }

/*
   assert(StreamHanoiLocal.hanoi(3, L, M, R).toList == hanoi3)
   assert(StreamHanoiLocal.hanoi(20, L, M, R).length == (1 << 20) - 1)
}

test("large hanoi") {
   assert(StreamHanoi.oneMove == ('M', 'L'))
   assert(StreamHanoi.anotherMove == ('L', 'R'))
   val h100 = StreamHanoiLocal.hanoi(100, 'L', 'M', 'R')
   assert(h100(999) == ('M', 'L'))
   assert(h100(49) == ('L', 'R'))
}
 */
