package chap10

import chap10.Section_7.*
import mocks.Chap09.{ date1, date2, date3 }
import org.scalatest.funsuite.AnyFunSuite

class Section_7_Tests extends AnyFunSuite:
   test("demo") {
      assert(demo == Map(0 -> List(2, 4, 10, 20), 1 -> List(5, 7, 1)))
   }

   test("tempsOn") {
      assert(tempsOn(date1) == List(date1 -> 89, date1 -> 93))
      assert(tempsOn(date2) == List(date2 -> 93))
      assert(tempsOn(date3) == List(date3 -> 88))
   }

   test("daysWith") {
      assert(daysWith(88) == List(date3 -> 88))
      assert(daysWith(89) == List(date1 -> 89))
      assert(daysWith(93) == List(date2 -> 93, date1 -> 93))
   }

   test("tempsOn (groupBy, then map)") {
      import GroupByThenMap.tempsOn
      assert(tempsOn(date1) == List(89, 93))
      assert(tempsOn(date2) == List(93))
      assert(tempsOn(date3) == List(88))
   }

   test("tempsOn (groupMap)") {
      import GroupMap.tempsOn
      assert(tempsOn(date1) == List(89, 93))
      assert(tempsOn(date2) == List(93))
      assert(tempsOn(date3) == List(88))
   }
