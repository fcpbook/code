package chap12

import scala.util.Random

// noinspection TypeAnnotation
object Section_7:
   def countUp(n: Int): LazyList[Int] = n #:: countUp(n + 1)

   val naturals = countUp(0) // 0, 1, 2, 3, ...

   def randomNumbers: LazyList[Float] = Random.nextFloat() #:: randomNumbers

   object Variant:
      val naturals      = LazyList.iterate(0)(_ + 1) // also: LazyList.from(0)
      val randomNumbers = LazyList.continually(Random.nextFloat())
