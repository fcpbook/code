package chap03

import scala.collection.mutable

//noinspection TypeAnnotation, ReferenceMustBePrefixed, ScalaUnusedExpression
object Section_5:
   object Demo1:
      import scala.collection.mutable.Set
      val set: Set[String] = Set("A", "B") // a mutable set
      set += "C"

   object Demo2:
      var set = Set("A", "B") // an immutable set
      set = set + "C"

   object Demo3:
      // DON'T DO THIS!
      val set = Set("A", "B") // an immutable set
      set + "C"
      // set is still {A,B}
