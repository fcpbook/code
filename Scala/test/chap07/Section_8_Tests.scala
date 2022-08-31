package chap07

import chap07.Section_8.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.util.FastRandom

class Section_8_Tests extends AnyFunSuite:
   private val nums       = List.fill(100)(FastRandom.nextInt(100))
   private val sortedNums = nums.sorted

   for (sort, name) <- Seq(
        (insertSort, "insert-sort"),
        (mergeSort, "merge-sort"),
        (quickSort, "quick-sort"),
        (MergeSortAlt.mergeSort, "merge-sort (alt)")
      )
   do
      test(name) {
         assert(sort(List.empty).isEmpty)
         assert(sort(List(0)) == List(0))
         assert(sort(nums) == sortedNums)
      }
