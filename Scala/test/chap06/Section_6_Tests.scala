package chap06

import chap06.Section_4.BinTree
import chap06.Section_4.BinTree.{ Empty, Node }
import chap06.Section_6.*
import chap06.Section_6.IterativeBinarySearch.search as search0
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*
import tinyscalautils.util.FastRandom

import java.lang.StackOverflowError
import scala.annotation.tailrec

class Section_6_Tests extends AnyFunSuite:
   for (search, name) <- Seq((search0, "iterative"), (search, "recursive")) do
      test(s"corner cases ($name)") {
         assert(search(IndexedSeq.empty, X).isEmpty)
         assert(search(IndexedSeq(M), A).isEmpty)
         assert(search(IndexedSeq(M), Z).isEmpty)
         assert(search(IndexedSeq(M), M).contains(0))
         assert(search(IndexedSeq(M, P), A).isEmpty)
         assert(search(IndexedSeq(M, P), N).isEmpty)
         assert(search(IndexedSeq(M, P), Z).isEmpty)
         assert(search(IndexedSeq(M, P), M).contains(0))
         assert(search(IndexedSeq(M, P), P).contains(1))
      }

      def randomTest(n: Int) =
         val i1, i2 = Iterator.fill(n)(FastRandom.nextString(10))
         val in     = i1.toIndexedSeq.sorted
         val out    = i2.toSet -- in
         for i   <- in.indices do assert(search(in, in(i)).contains(i))
         for str <- out do assert(search(in, str).isEmpty)

      test(s"random  ($name)") {
         for n <- 1 to 1000 do randomTest(n)
      }
   end for

   test("last") {
      assertThrows[NoSuchElementException](last(Nil))
      assert(last(List(A, B, C)) == C)
      assert(last(List.range(0, 1_000_000)) == 999_999)
   }

   test("factorial") {
      assert(factorial(10) == 3_628_800)
      assert(factorial(1_000_000) == 0)
   }

   private val abc = Node(A, Node(B, Empty, Empty), Node(C, Empty, Empty))
   private def bigTree(depth: Int) =
      @tailrec def loop(left: BinTree[String], right: BinTree[String], n: Int): BinTree[String] =
         if n == 0 then Node(X, left, right)
         else loop(Node(L, left, Empty), Node(R, Empty, right), n - 1)
      loop(Empty, Empty, depth)

   test("size, still not tailrec") {
      import SizeAttempt.size
      assert(size(Empty) == 0)
      assert(size(abc) == 3)
      assertThrows[StackOverflowError](size(bigTree(1_000_000)))
   }

   test("size, tailrec") {
      assert(size(Empty) == 0)
      assert(size(abc) == 3)
      assert(size(bigTree(1_000_000)) == 2_000_001)
   }
