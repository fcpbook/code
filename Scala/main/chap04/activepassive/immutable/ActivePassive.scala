package chap04.activepassive.immutable

final class ActivePassive[A] private (elements: Set[A], activeSet: Set[A]):
   def this(elements: Set[A]) = this(elements, Set.empty[A])

   @throws[IllegalArgumentException]("if elem is not a set element")
   def isActive(elem: A): Boolean =
      require(elements.contains(elem), "not a set element")
      activeSet.contains(elem)

   @throws[IllegalArgumentException]("if elem is not a set element")
   def isPassive(elem: A): Boolean = !isActive(elem)

   def allActive: Set[A] = activeSet

   def allPassive: Set[A] = elements diff activeSet

   def isAllActive: Boolean = activeSet.size == elements.size

   def isAllPassive: Boolean = activeSet.isEmpty

   @throws[IllegalArgumentException]("if elem is not a set element")
   def activate(elem: A): ActivePassive[A] =
      require(elements.contains(elem), "not a set element")
      ActivePassive(elements, activeSet + elem)

   @throws[IllegalArgumentException]("if elem is not a set element")
   def deactivate(elem: A): ActivePassive[A] =
      require(elements.contains(elem), "not a set element")
      ActivePassive(elements, activeSet - elem)

   def activateAll(): ActivePassive[A] = ActivePassive(elements, elements)

   def deactivateAll(): ActivePassive[A] = ActivePassive(elements, Set.empty)
end ActivePassive
