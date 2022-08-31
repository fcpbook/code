package chap10

import chap03.Section_9.ImmutableLoads.Load as ILoad
import chap03.Section_9.MutableLoads.Load
import chap10.Section_1.temps
import chap10.Section_3.{ applyOperation, getAccount, parseRequest }
import chap10.Section_8.BinTree
import mocks.Chap10.{ Account, Operation, Request }

object Section_9:
   def demo1(request: Request, op: Operation): Option[Account] =
      for
         user    <- parseRequest(request)
         account <- getAccount(user)
         result  <- applyOperation(account, op)
      yield result

   def demo2(loads: List[ILoad]): List[ILoad] = for load <- loads yield load.reduced

   def demo3(loads: List[ILoad]): List[ILoad] = loads.map(load => load.reduced)

   def demo4(loads: List[Load]): Unit = for load <- loads do load.reduce()

   def demo5(loads: List[Load]): Unit = loads.foreach(load => load.reduce())

   def demo6(loads: List[ILoad]): List[ILoad] =
      for load <- loads if load.weight != 0 yield load.reduced

   def demo7(loads: List[ILoad]): List[ILoad] =
      loads.withFilter(load => load.weight != 0).map(load => load.reduced)

   def demo8(): Unit =
      val tree: BinTree = mocks.Chap10.tree
      for x <- tree do println(x) // prints all the values in the tree, in order

   def demo9: List[Int] = for temp <- temps if temp > 75 yield ((temp - 32) / 1.8f).round
