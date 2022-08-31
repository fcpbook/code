package chap10

import chap10.Section_8.*
import mocks.Chap10.tree
import org.scalatest.funsuite.AnyFunSuite

class Section_8_Tests extends AnyFunSuite:
   test("exists") {
      for n <- 0 to 10 do assert(tree.exists(_ == n) == (1 <= n && n <= 5))
   }

   test("foreach") {
      val b = List.newBuilder[Int]
      for x <- tree do b += x
      assert(b.result() == List.range(1, 6))
   }

   test("fold") {
      val list = tree.fold(List.empty[Int])((acc, x) => x :: acc)
      assert(list == List.range(5, 0, -1))
   }
