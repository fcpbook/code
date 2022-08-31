package chap10

import chap10.Aside.*
import mocks.Chap10.Functor.f
import org.scalatest.funsuite.AnyFunSuite

class AsideTests extends AnyFunSuite:
   test("demos") {
      assert(Functor.demo1)
      assert(Functor.demo2)
      assert(Functor.demo3)
      assert(Functor.demo4)
      assert(Monad.demo1)
      assert(Monad.demo2)
      assert(Monad.demo3)
   }

   test("map") {
      for n <- 1 to 10 do
         val struct = List.range(1, n)
         assert(Monad.map(struct)(f) == struct.map(f))
   }
