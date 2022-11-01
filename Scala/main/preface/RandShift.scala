package preface

import scala.util.Random

/*
object RandShift1:
   def randShift(nums: List[Int], rand: Random): List[Int] = {
      val shiftedNums = List.newBuilder[Int]
      for (num <- nums) {
         val shifted = num + rand.between(-10, 11)
         if (shifted > 0) {
            shiftedNums += shifted
         }
      }
      shiftedNums.result()
   }
*/

object RandShift2:
   def randShift(nums: List[Int], rand: Random): List[Int] =
      nums.view
         .map(num => num + rand.between(-10, 11))
         .filter(shifted => shifted > 0)
         .toList

object RandShift3:
   def randShift(nums: List[Int], rand: Random): List[Int] =
      for {
         num <- nums
         shifted = num + rand.between(-10, 11)
         if shifted > 0
      } yield shifted

object RandShift4:
   def randShift(nums: List[Int], rand: Random): List[Int] =
      val shiftedNums = List.newBuilder[Int]
      for num <- nums do
         val shifted = num + rand.between(-10, 11)
         if shifted > 0 then shiftedNums += shifted
      end for
      shiftedNums.result()
   end randShift

object RandShift5:
   def randShift(nums: List[Int], rand: Random): List[Int] =
      val shiftedNums = List.newBuilder[Int]
      for num <- nums do
         val shifted = num + rand.between(-10, 11)
         if shifted > 0 then shiftedNums += shifted
      shiftedNums.result()
