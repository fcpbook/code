package chap03

import scala.collection.mutable

//noinspection TypeAnnotation, ReferenceMustBePrefixed
object Section_6:
   object Demo1:
      import scala.collection.mutable.Set
      val set = Set("A", "B") // a mutable set
      set += "C" // a call to method +=; that is, set.+=("C")
      // set is now {A,B,C}

   object Demo2:
      var set = Set("A", "B") // an immutable set
      set += "C" // a reassignment of a var; that is, set = set + "C"
      // set is now {A,B,C}

   object Demo3:
      val set1 = Set(1, 2, 3)

      import scala.collection.mutable
      val set2 = mutable.Set(1, 2, 3)

      import scala.collection.mutable.Set as MutableSet
      val set3 = MutableSet(1, 2, 3)
   end Demo3
