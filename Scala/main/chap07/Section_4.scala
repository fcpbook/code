package chap07

object Section_4:
   def take[A](list: List[A], n: Int): List[A] = (list, n) match
      case (_, 0) | (Nil, _) => Nil
      case (head :: tail, _) => head :: take(tail, n - 1)
