package chap10

import chap10.Section_1.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.util.FastRandom

class Section_1_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1)
      assert(!demo2)
      assert(demo3)
      assert(demo4)
      assert(demo5)
      assert(!demo6)
      assert(!demo7)
      assert(!demo8)
      assert(demo9)
      assert(demo10 == 3)
      assert(demo11 == 2)
      assert(demo12 == List(88, 91, 78, 100, 98))
      assert(demo13 == List(69, 70))
      assert(demo14 == (List(88, 91, 78, 100, 98), List(69, 70)))
      assert(demo15 == List(88, 91, 78))
      assert(demo16 == List(69, 100, 98, 70))
      assert(demo17 == List(88, 91, 78, 0, 100, 98, 70))
   }

   test("quick-sort") {
      val nums = List.fill(100)(FastRandom.nextInt(100))
      assert(quickSort(List.empty).isEmpty)
      assert(quickSort(List(0)) == List(0))
      assert(quickSort(nums) == nums.sorted)
   }

   test("find") {
      for l <- Seq(Nil, List(1), List(0), List(1, 0), List(0, 1), List(1, 2), List(0, 0)) do
         assert(find(l, _ > 0) == l.find(_ > 0))
   }
