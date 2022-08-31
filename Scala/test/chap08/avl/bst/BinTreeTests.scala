package chap08.avl.bst

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.withThreadPoolAndWait
import tinyscalautils.util.{ FastRandom, log2 }

import scala.concurrent.Future
import scala.concurrent.duration.Duration

class BinTreeTests extends AnyFunSuite:
   /* Simple isAVL implementation acceptable since height is stored as a val. */
   private def isAVL(tree: BinTree): Boolean = tree match
      case Empty                => true
      case Node(_, left, right) => tree.imbalance.abs <= 1 && isAVL(left) && isAVL(right)

   private def insertDelete(maxSize: Int) =
      val size    = FastRandom.nextInt(maxSize)
      var numbers = FastRandom.shuffle(List.range(0, size))
      var set     = BinTree.empty
      var count   = 0
      for x <- numbers do
         assert(set.size == count)
         assert(!set.contains(x))
         val h = set.height
         set = set + x
         count += 1
         assert(set.size == count)
         assert(set.contains(x))
         assert(set.toList == numbers.take(count).sorted)
         assert(set.height <= h + 1)
         assert(isAVL(set))
      numbers = FastRandom.shuffle(numbers)
      for x <- numbers do
         assert(set.size == count)
         assert(set.contains(x))
         val h = set.height
         set = set - x
         count -= 1
         assert(set.size == count)
         assert(!set.contains(x))
         assert(set.toList == numbers.drop(size - count).sorted)
         assert(set.height >= h - 1)
         assert(isAVL(set))

   test("random trees") {
      withThreadPoolAndWait(tinyscalautils.threads.Executors.global) {
         val f1 = Future(1000000 times insertDelete(10))
         val f2 = Future(10000 times insertDelete(100))
         val f3 = Future(100 times insertDelete(1000))
         val f4 = Future(10 times insertDelete(10000))
         Future.sequence(List(f1, f2, f3, f4))
      }
   }

   private def fromRandomSet(maxSize: Int) =
      val size = FastRandom.between(1, maxSize + 1)
      val nums = Set.fill(size)(FastRandom.nextInt(maxSize))
      val tree = BinTree.fromSet(nums)
      assert(tree.toList == nums.toSeq.sorted)
      assert(tree.height == log2(nums.size) + 1)

   test("fromSet, random") {
      for maxSize <- Seq.iterate(1, 7)(_ * 10) do fromRandomSet(maxSize)
   }

   test("fromSet") {
      assert(BinTree.fromSet(Set.empty).isEmpty)
   }
