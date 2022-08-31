package chap10

import chap09.Section_1.temps
import chap09.Section_3.datedTemps

import java.time.LocalDate

object Section_6:
   def demo1: List[Int] = temps.sortWith(_ < _) // List(69, 70, 78, 88, 91, 98, 100)

   def demo2: List[Int] = temps.sortWith(_ > _) // List(100, 98, 91, 88, 78, 70, 69)

   val strings: List[String] = mocks.Chap10.strings

   def demo3: List[String] = strings.sortWith(_.length < _.length)

   def demo4: List[String] = strings.sortBy(_.length)

   def demo5: List[Int] = strings.map(_.length).sorted

   val points: List[(Double, Double)] = mocks.Chap10.points

   def demo6: List[(Double, Double)] = points.sortBy((x, y) => x * x + y * y)

   val demo7: Int              = temps.max      // highest temperature
   val demo8: (LocalDate, Int) = datedTemps.max // highest temperature on the last date
   val demo9: (LocalDate, Int) = datedTemps.maxBy((_, temp) => temp) // highest temperature

   val byTemp: Ordering[(LocalDate, Int)] = Ordering.by((_, temp) => temp)
   val byDate: Ordering[(LocalDate, Int)] = Ordering.by((date, _) => date)

   def demo10: (LocalDate, Int) = datedTemps.max(byTemp.orElse(byDate.reverse))
