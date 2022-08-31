package chap10

import mocks.Chap09.Project

//noinspection ZeroIndexToHead,TypeAnnotation
object Section_1:
   val temps = List(88, 91, 78, 69, 100, 98, 70)

   def demo1: Boolean = temps.exists(_ > 90) // true

   def demo2: Boolean = temps.exists(_ > 100) // false

   def demo3: Boolean = temps.forall(temp => 32 <= temp && temp <= 100) // true

   def demo4: Boolean = Some("foo").exists(_.endsWith("o")) // true

   def demo5: Boolean = Some("foo").forall(_.endsWith("o")) // true

   def demo6: Boolean = Some("bar").exists(_.endsWith("o")) // false

   def demo7: Boolean = Some("bar").forall(_.endsWith("o")) // false

   def demo8: Boolean = None.exists((proj: Project) => proj.id == 12345L) // false

   def demo9: Boolean = None.forall((proj: Project) => proj.id == 12345L) // true   <- BE CAREFUL HERE!

   def demo10: Int = temps.count(_ > 90) // 3

   def demo11: Int = temps.sliding(2).count(pair => pair(1) > pair(0)) // 2

   def demo12: List[Int] = temps.filter(_ > 75) // List(88, 91, 78, 100, 98)

   def demo13: List[Int] = temps.filterNot(_ > 75) // List(69, 70)

   def demo14: (List[Int], List[Int]) = temps.partition(_ > 75) // (List(88, 91, 78, 100, 98), List(69, 70))

   def quickSort(list: List[Int]): List[Int] = list match
      case Nil => list
      case pivot :: others =>
         val (low, high) = others.partition(_ < pivot)
         quickSort(low) ::: pivot :: quickSort(high)

   def demo15: List[Int] = temps.takeWhile(_ > 75) // List(88, 91, 78)

   def demo16: List[Int] = temps.dropWhile(_ > 75) // List(69, 100, 98, 70)

   def find[A](list: List[A], test: A => Boolean): Option[A] =
      list.dropWhile(!test(_)).headOption

   def demo17: List[Int] =
      temps.span(_ > 75) match
         case (all, Nil)         => all
         case (left, _ :: right) => left ::: 0 :: right
