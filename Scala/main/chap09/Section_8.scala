package chap09

object Section_8:
   def findGreaterThan90(list: List[Int]): Option[Int] =
      var rem = list
      while rem.nonEmpty do if rem.head > 90 then return Some(rem.head) else rem = rem.tail
      None
