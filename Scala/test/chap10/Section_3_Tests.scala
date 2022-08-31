package chap10

import chap10.Section_3.*
import org.scalatest.funsuite.AnyFunSuite

class Section_3_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1 == List(List(1, 1, 1), List(2, 2, 2), List(3, 3, 3)))
      assert(demo2 == List(1, 1, 1, 2, 2, 2, 3, 3, 3))
      assert(demo4.contains(43))
      assertResult(Seq(None, None, None, Some(0))) {
         for req <- 0 to 3 yield demo3(req, 0)
      }
      assert(demo5.isEmpty)
      assertResult(Seq(None, Some(None), Some(Some(None)), Some(Some(Some(0))))) {
         for req <- 0 to 3 yield demo6(req, 0)
      }
      assertResult(Seq(None, None, None, Some(0))) {
         for req <- 0 to 3 yield demo7(req, 0)
      }
      assert(NoOption.demo(0, 0) == 0)
   }

   test("filter") {
      assert(filter(Nil, _ => true) == Nil.filter(_ => true))
      for n <- 1 to 10; m <- 0 to 10 do
         val list = List.range(1, n)
         assert(filter(list, _ > m) == list.filter(_ > m))
   }

   test("map") {
      for n <- 1 to 10 do
         val list = List.range(1, n)
         assert(map(list, _ + 1) == list.map(_ + 1))
   }
