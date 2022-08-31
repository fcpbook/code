package chap09

import chap09.Section_1.temps
import mocks.Chap09.{ Project, projects }

import java.time.LocalDate
import java.util.Date

object Section_3:
   def demo1: Int => Int = (x: Int) => x + 1

   def demo2: Option[Int]     = temps.find((temp: Int) => temp > 90)
   def demo3: Option[Project] = projects.find((proj: Project) => proj.id == 12345L)

   object DeclaredTypes:
      def greaterThan(bound: Int): Int => Boolean   = (x: Int) => x > bound
      def hasID(identity: Long): Project => Boolean = (p: Project) => p.id == identity

   def demo4: Option[Int]     = temps.find(temp => temp > 90)
   def demo5: Option[Project] = projects.find(proj => proj.id == 12345L)

   def greaterThan(bound: Int): Int => Boolean   = x => x > bound
   def hasID(identity: Long): Project => Boolean = p => p.id == identity

   val datedTemps: List[(LocalDate, Int)] = mocks.Chap09.datedTemps

   def demo6: Option[(LocalDate, Int)] =
      datedTemps.find(dt => dt(0).getMonthValue <= 3 && dt(1) > 90)

   def demo7: Option[(LocalDate, Int)] =
      datedTemps.find((date, temp) => date.getMonthValue <= 3 && temp > 90)

   val optionalDatedTemps: List[(Option[LocalDate], Int)] = mocks.Chap09.optionalDatedTemps

   def demo8: Option[(Option[LocalDate], Int)] = optionalDatedTemps.find {
      case (Some(date), temp) => date.getMonthValue <= 3 && temp > 90
      case _                  => false
   }

   def demo9: [A] => ((A, A)) => A = [A] => (p: (A, A)) => p(0)
