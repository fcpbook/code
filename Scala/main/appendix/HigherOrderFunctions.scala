package appendix

import java.util.Comparator

//noinspection TypeAnnotation
object HigherOrderFunctions:
   val byLength: Comparator[String] = (a, b) => a.length.compareTo(b.length)

   object Len1:
      val len: Any = (str: String) => str.length // OK

   object Len2:
      val len = (str: String) => str.length // type String => Int inferred

   def existsOrEmpty[A](list: List[A])(test: A => Boolean): Boolean =
      list.isEmpty || list.exists(test)

   val e = existsOrEmpty(List(1, 2, 3)) { num =>
      num > 1
   }
end HigherOrderFunctions
