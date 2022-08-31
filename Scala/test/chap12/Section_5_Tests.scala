package chap12

import chap12.Section_5.*
import mocks.Chap12.Data
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.StringLetters.{ L, M, R }

private val hanoi3 = List((L, R), (L, M), (R, M), (L, R), (M, L), (M, R), (L, R))

class Section_5_Tests extends AnyFunSuite:
   private val someData = LazyList(Data(3), Data(10), Data(11), Data(1))

   test("search") {
      assert(SearchList.searchData(someData.take(2).toList).isEmpty)
      assert(SearchList.searchData(someData.toList).contains(Data(11)))
      assert(SearchByNameList.searchData(someData.take(2).toList).isEmpty)
      assert(SearchByNameList.searchData(someData.toList).contains(Data(11)))
      assert(SearchThunkList.searchData(someData.map(d => () => d).take(2).toList).isEmpty)
      assert(SearchThunkList.searchData(someData.map(d => () => d).toList).contains(Data(11)))
      assert(Variant1.searchData(someData.take(2)).isEmpty)
      assert(Variant1.searchData(someData).contains(Data(11)))
      assert(Variant2.searchData(someData.take(2)).isEmpty)
      assert(Variant2.searchData(someData).contains(Data(11)))
      assert(searchData(someData.take(2)).isEmpty)
      assert(searchData(someData).contains(Data(11)))
   }

   test("hanoi") {
      assert(ListHanoi.hanoi(3, L, M, R) == hanoi3)
      assert(ListHanoi.hanoi(20, L, M, R).length == (1 << 20) - 1)
      assert(hanoi(3, L, M, R) == hanoi3)
      assert(hanoi(20, L, M, R).length == (1 << 20) - 1)
      assert(oneMove == ('M', 'L'))
      assert(anotherMove == ('L', 'R'))
   }
