package chap04.activepassive.functional

opaque type ActivePassive[A] = (Set[A], Set[A])

private def elements[A](ap: ActivePassive[A]): Set[A]  = ap(0)
private def activeSet[A](ap: ActivePassive[A]): Set[A] = ap(1)

def createActivePassive[A](elements: Set[A]): ActivePassive[A] = (elements, Set.empty)

@throws[IllegalArgumentException]("if elem is not a set element")
def isActive[A](ap: ActivePassive[A], elem: A): Boolean =
   require(elements(ap).contains(elem), "not a set element")
   activeSet(ap).contains(elem)

@throws[IllegalArgumentException]("if elem is not a set element")
def isPassive[A](ap: ActivePassive[A], elem: A): Boolean = !isActive(ap, elem)

def allActive[A](ap: ActivePassive[A]): Set[A] = activeSet(ap)

def allPassive[A](ap: ActivePassive[A]): Set[A] = elements(ap) diff activeSet(ap)

def isAllActive[A](ap: ActivePassive[A]): Boolean =
   activeSet(ap).size == elements(ap).size

def isAllPassive[A](ap: ActivePassive[A]): Boolean = activeSet(ap).isEmpty

@throws[IllegalArgumentException]("if elem is not a set element")
def activate[A](ap: ActivePassive[A], elem: A): ActivePassive[A] =
   require(elements(ap).contains(elem), "not a set element")
   (elements(ap), activeSet(ap) + elem)

@throws[IllegalArgumentException]("if elem is not a set element")
def deactivate[A](ap: ActivePassive[A], elem: A): ActivePassive[A] =
   require(elements(ap).contains(elem), "not a set element")
   (elements(ap), activeSet(ap) - elem)

def activateAll[A](ap: ActivePassive[A]): ActivePassive[A] =
   (elements(ap), elements(ap))

def deactivateAll[A](ap: ActivePassive[A]): ActivePassive[A] =
   (elements(ap), Set.empty)
