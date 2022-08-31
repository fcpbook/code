package chap09

import mocks.Chap09.{ Project, projects }

import scala.annotation.tailrec

//noinspection TypeAnnotation
object Section_1:
   val temps = List(88, 91, 78, 69, 100, 98, 70)

   @tailrec
   def find[A](list: List[A], target: A): Option[A] = list match
      case Nil    => None
      case h :: t => if h == target then Some(h) else find(t, target)

   def demo1: Option[Int] = find(temps, 78) // Some(78)
   def demo2: Option[Int] = find(temps, 79) // None

   @tailrec
   def findGreaterThan90(list: List[Int]): Option[Int] = list match
      case Nil    => None
      case h :: t => if h > 90 then Some(h) else findGreaterThan90(t)

   def demo3: Option[Int] = findGreaterThan90(temps) // Some(91)

   @tailrec
   def findGreaterThan(list: List[Int], bound: Int): Option[Int] = list match
      case Nil    => None
      case h :: t => if h > bound then Some(h) else findGreaterThan(t, bound)

   def demo4: Option[Int] = findGreaterThan(temps, 80) // Some(88)

   @tailrec
   def find[A](list: List[A], test: A => Boolean): Option[A] = list match
      case Nil    => None
      case h :: t => if test(h) then Some(h) else find(t, test)

   def greaterThan90(x: Int): Boolean = x > 90

   def demo5: Option[Int] = find(temps, greaterThan90) // Some(91)

   def hasID12345(project: Project): Boolean = project.id == 12345L

   def demo6: Option[Project] = find(projects, hasID12345) // project with identity 12345

   def demo7: Option[Int]     = temps.find(greaterThan90)
   def demo8: Option[Project] = projects.find(hasID12345)

   def greaterThan(bound: Int): Int => Boolean =
      def greaterThanBound(x: Int): Boolean = x > bound
      greaterThanBound

   def demo9: Option[Int]  = temps.find(greaterThan(90))
   def demo10: Option[Int] = temps.find(greaterThan(80))

   def hasID(identity: Long): Project => Boolean =
      def hasGivenID(project: Project): Boolean = project.id == identity
      hasGivenID

   def demo11: Option[Project] = projects.find(hasID(12345L))
   def demo12: Option[Project] = projects.find(hasID(54321L))
