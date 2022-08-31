package appendix

//noinspection TypeAnnotation
object Immutability:
   val a: List[String] = List("A", "B", "C")
   val b               = a.tail // constant time, lists a and b share all their data
