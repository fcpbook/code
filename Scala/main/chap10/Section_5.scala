package chap10

import tinyscalautils.text.StringLetters.X

object Section_5:
   def demo1(n: Int, f: Int => String): List[String] =
      List.tabulate(n)(f) // List(f(0), f(1), f(2), ... , f(n-1))

   def demo2: List[String] =
      List.tabulate(5)(i => "X" * i) // List("", "X", "XX", "XXX", "XXXX")

   def demo3(n: Int, f: String => String): List[String] =
      List.iterate(X, n)(f) // List(X, f(X), f(f(X)), f(f(f(X))), ...)

   def demo4: List[String] =
      List.iterate("", 5)(str => str + "X") // List("", "X", "XX", "XXX", "XXXX")

   def demo5(f: String => Option[(String, String)]): List[String] =
      List.unfold(X)(f) // List(fVal(X), fVal(fNext(X)), fVal(fNext(fNext(X))), ...)

   def demo6: List[String] =
      List.unfold("XXXX")(str => if str.isEmpty then None else Some((str, str.tail)))
      // List("XXXX", "XXX", "XX", "X")
