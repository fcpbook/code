package chap04.activepassive.mutable1

import scala.collection.mutable

final class ActivePassive[A](elements: Set[A]):
   private val activeSet = mutable.Set.empty[A]

   @throws[IllegalArgumentException]("if elem is not a set element")
   def isActive(elem: A): Boolean =
      require(elements.contains(elem), "not a set element")
      activeSet.contains(elem)

   @throws[IllegalArgumentException]("if elem is not a set element")
   def isPassive(elem: A): Boolean = !isActive(elem)

   def allActive: Set[A] = activeSet.toSet

   def allPassive: Set[A] = elements diff activeSet

   def isAllActive: Boolean = activeSet.size == elements.size

   def isAllPassive: Boolean = activeSet.isEmpty

   @throws[IllegalArgumentException]("if elem is not a set element")
   def activate(elem: A): Unit =
      require(elements.contains(elem), "not a set element")
      activeSet += elem

   @throws[IllegalArgumentException]("if elem is not a set element")
   def deactivate(elem: A): Unit =
      require(elements.contains(elem), "not a set element")
      activeSet -= elem

   def activateAll(): Unit = activeSet ++= elements

   def deactivateAll(): Unit = activeSet.clear()
end ActivePassive
