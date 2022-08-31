package chap03

import java.util
import scala.collection.mutable

object Section_2:
   // noinspection ReferenceMustBePrefixed
   def demo(aMutableSet: mutable.Set[String]): mutable.Set[String] =
      import mutable.Set
      val set: Set[String] = aMutableSet
      set += "X" -= "Y"

   // pure function, cannot be used as an action
   def getFirstString(strings: util.List[String]): String = strings.get(0)

   // impure function, can be used as an action
   def getAndRemoveFirstString(strings: util.List[String]): String = strings.remove(0)

   // action, cannot be used as a function
   def removeFirstString(strings: util.List[String]): Unit = strings.remove(0)
