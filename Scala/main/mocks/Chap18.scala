package mocks

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicInteger
import scala.annotation.unused

object Chap18:
   extension (num: AtomicInteger) def increment(): Unit = num.incrementAndGet()

   private val par2 = CyclicBarrier(2)

   def getRank(): Option[Int] =
      par2.await()
      Some(1)

   def play(@unused rank: Int): Unit = par2.await()

   val X, Y = Object()
