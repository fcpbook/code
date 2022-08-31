package chap07

import chap07.Section_7.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*
import tinyscalautils.timing.timeOf
import tinyscalautils.util.average

class Section_7_Tests extends AnyFunSuite:
   private val abc    = List(A, B, C)
   private val medium = List.range(0, 5000)
   private val large  = List.range(0, 1_000_000)

   test("reverse, bad") {
      import ReverseBad.reverse
      assert(reverse(Nil) == Nil.reverse)
      assert(reverse(abc) == abc.reverse)
      assertThrows[StackOverflowError](reverse(large))
   }

   test("reverse") {
      assert(reverse(Nil) == Nil.reverse)
      assert(reverse(abc) == abc.reverse)
      assert(reverse(large) == large.reverse)
   }

   private def timedRun(rev: List[Int] => List[Int]): Double =
      var r = List.empty[Int]
      val time = timeOf {
         r = rev(medium)
      }
      assert(r.head == medium.last)
      time

   test("timing") {
      val (t1, t2) = Seq.fill(100)((timedRun(ReverseBad.reverse), timedRun(reverse))).unzip
      val (a1, a2) = (average(t1, ignoredPairs = 2), average(t2, ignoredPairs = 2))
      println(s"bad reverse: $a1")
      println(s"good reverse: $a2")
      assert(a1 / a2 > 500.0)
   }

   test("tokenize") {
      assert(tokenize(Nil).isEmpty)
      assert(tokenize(" \n\t\r".toList).isEmpty)
      assert(tokenize("  ABC  DEF\n\n".toList) == List("ABC", "DEF"))
   }
