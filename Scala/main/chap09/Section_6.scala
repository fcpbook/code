package chap09

import chap09.Section_1.temps
import mocks.Chap09.{ Project, celsiusTemps, projects }

object Section_6:
   def demo1: Option[Int] = celsiusTemps.find(temp => temp * 1.8 + 32 > 90)
   def demo2: Option[Int] = celsiusTemps.find(_ * 1.8 + 32 > 90)

   def demo3: Option[Int]     = temps.find(_ > 90)
   def demo4: Option[Project] = projects.find(_.id == 12345L)
