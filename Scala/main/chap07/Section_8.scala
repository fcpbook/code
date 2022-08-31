package chap07

import chap07.Section_2.length
import chap07.Section_5.concat
import chap07.Section_6.splitAt

object Section_8:
   def insertInSorted(x: Int, sorted: List[Int]): List[Int] = sorted match
      case Nil => List(x)
      case min :: others =>
         if x < min then x :: sorted else min :: insertInSorted(x, others)

   def insertSort(list: List[Int]): List[Int] = list match
      case Nil    => list
      case h :: t => insertInSorted(h, insertSort(t))

   def merge(sortedA: List[Int], sortedB: List[Int]): List[Int] = (sortedA, sortedB) match
      case (Nil, _) => sortedB
      case (_, Nil) => sortedA
      case (hA :: tA, hB :: tB) =>
         if hA <= hB then hA :: merge(tA, sortedB) else hB :: merge(sortedA, tB)

   def mergeSort(list: List[Int]): List[Int] = list match
      case Nil | List(_) => list
      case _ =>
         val (left, right) = splitAt(list, length(list) / 2)
         merge(mergeSort(left), mergeSort(right))

   object MergeSortAlt:
      def mergeSort(list: List[Int]): List[Int] = length(list) match
         case 0 | 1 => list
         case len =>
            val (left, right) = splitAt(list, len / 2)
            merge(mergeSort(left), mergeSort(right))

   def splitPivot(pivot: Int, list: List[Int]): (List[Int], List[Int]) = list match
      case Nil => (Nil, Nil)
      case h :: t =>
         val (low, high) = splitPivot(pivot, t)
         if h < pivot then (h :: low, high) else (low, h :: high)

   def quickSort(list: List[Int]): List[Int] = list match
      case Nil => list
      case pivot :: others =>
         val (low, high) = splitPivot(pivot, others)
         concat(quickSort(low), pivot :: quickSort(high))
