package chap12

import chap12.Section_11.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.util.FastRandom

class Section_11_Tests extends AnyFunSuite:
   private def numbers   = FastRandom.shuffle(List(-1, 3, -6, -6, 11, 7, 3))
   private val solutions = Set(List(-6, 3, 3, 7, 11), List(7, 11))

   test("findSum") {
      def testFind(find: (Int, List[Int]) => Option[List[Int]]) =
         assert(find(19, numbers).isEmpty)
         assert(find(18, numbers).map(_.sorted).exists(solutions))

      testFind(findSum)
      testFind(Bad.findSum)
      testFind(Good.findSum)
      testFind(findShortestSum)
   }

   test("findAllSums") {
      def testFind(findAll: (Int, List[Int]) => Iterable[List[Int]]) =
         assert(findAll(19, numbers).isEmpty)
         assert(findAll(18, numbers.sorted).toSet == solutions)

      testFind(findAllSums)
      testFind(Good.findAllSums)
      testFind(lazyFindAllSums)
   }

   test("findShortestSum") {
      100 times {
         assert(findShortestSum(18, numbers).exists(_.lengthIs == 2))
      }
   }
