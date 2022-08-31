package chap13

object Section_2:
   def search[A](values: List[A], target: A): Option[Int] = mocks.Chap13.searchOpt(values, target)

   def between[A](values: List[A], from: A, to: A): List[A] =
      (search(values, from), search(values, to)) match
         case (Some(i), Some(j)) => values.slice(i min j, (i max j) + 1)
         case _                  => List.empty
