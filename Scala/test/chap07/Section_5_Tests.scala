package chap07

import chap07.Section_5.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.*

class Section_5_Tests extends AnyFunSuite:
   private val abc   = List(A, B, C)
   private val large = List.range(0, 1_000_000)

   test("demos") {
      assert(demo1 == List(A, B, C, A, B, C))
      assert(demo2 == List(1, 2, 3, 4, 5, 6))
      assert(demo3 == List(1, 2, 3, 4, 5, 6))
      assert(demo4 == List((A, 1), (B, 2), (C, 3)))
   }

   test("concat") {
      assert(concat(Nil, Nil) == Nil ::: Nil)
      assert(concat(abc, Nil) == abc ::: Nil)
      assert(concat(Nil, abc) == Nil ::: abc)
      assert(concat(abc, abc) == abc ::: abc)
      assert(concat(abc, large) == abc ::: large)
   }

   test("append") {
      assert(append(Nil, X) == Nil :+ X)
      assert(append(abc, X) == abc :+ X)
   }

   test("flatten") {
      assert(flatten(Nil) == Nil.flatten)
      assert(flatten(List(Nil)) == List(Nil).flatten)
      assert(flatten(List(abc, abc)) == List(abc, abc).flatten)
      assert(flatten(List(abc, Nil, abc)) == List(abc, Nil, abc).flatten)
      assert(flatten(List(abc, Nil, X :: abc)) == List(abc, Nil, X :: abc).flatten)
   }

   test("zip") {
      assert(zip(Nil, Nil) == Nil.zip(Nil))
      assert(zip(abc, Nil) == abc.zip(Nil))
      assert(zip(Nil, abc) == Nil.zip(abc))
      assert(zip(abc, abc) == abc.zip(abc))
      assert(zip(abc, X :: abc) == abc.zip(X :: abc))
      assert(zip(X :: abc, abc) == (X :: abc).zip(abc))
   }
