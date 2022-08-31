package chap10

import chap09.Section_3.datedTemps

import java.time.LocalDate

object Section_7:
   def demo: Map[Int, List[Int]] = List(2, 5, 4, 10, 7, 1, 20).groupBy(n => n % 2)

   val tempsOn: Map[LocalDate, List[(LocalDate, Int)]] = datedTemps.groupBy((date, _) => date)

   val daysWith: Map[Int, List[(LocalDate, Int)]] = datedTemps.groupBy((_, temp) => temp)

   object GroupByThenMap:
      val tempsOn: Map[LocalDate, List[Int]] =
         datedTemps.groupBy((d, _) => d).map((d, list) => (d, list.map((_, t) => t)))

   object GroupMap:
      val tempsOn: Map[LocalDate, List[Int]] =
         datedTemps.groupMap((d, _) => d)((_, t) => t) // of type Map[LocalDate, List[Int]]
