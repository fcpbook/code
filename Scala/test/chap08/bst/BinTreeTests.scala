package chap08.bst

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.threads.withThreadPoolAndWait
import tinyscalautils.util.{ FastRandom, log2 }

import scala.collection.immutable.BitSet
import scala.concurrent.Future

class BinTreeTests extends AnyFunSuite:
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

   test("random trees") {
      withThreadPoolAndWait(tinyscalautils.threads.Executors.global) {
         val f1 = Future(1000000 times insertDelete(10))
         val f2 = Future(10000 times insertDelete(100))
         val f3 = Future(100 times insertDelete(1000))
         val f4 = Future(10 times insertDelete(10000))
         Future.sequence(Seq(f1, f2, f3, f4))
      }
   }

   test("fromSet") {
      import BinTree.fromSet
      assert(fromSet(Set.empty).isEmpty)
      val tree = fromSet(BitSet.fromSpecific(1 to 15))
      assert(tree.toList == (1 to 15))
      assert(tree.height == 4)
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

   test("demo tree") {
      val demoTree =
         Node(
           57,
           Node(
             43,
             Node(20, Node(6, Empty, Empty), Node(32, Empty, Empty)),
             Node(51, Empty, Node(52, Empty, Empty))
           ),
           Node(71, Node(60, Empty, Empty), Node(83, Node(78, Empty, Empty), Empty))
         )
      val (n, t) = demoTree.right.minRemoved
      assert(n == 60)
      assert(t == Node(71, Empty, Node(83, Node(78, Empty, Empty), Empty)))
      assert(
        demoTree - 57 ==
           Node(
             60,
             Node(
               43,
               Node(20, Node(6, Empty, Empty), Node(32, Empty, Empty)),
               Node(51, Empty, Node(52, Empty, Empty))
             ),
             Node(71, Empty, Node(83, Node(78, Empty, Empty), Empty))
           )
      )
   }
