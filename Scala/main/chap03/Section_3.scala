package chap03

object Section_3:
   def demo1(num: Int): String = if num > 100 then "large" else "small"
   def demo2(num: Int): String = (if num > 100 then "large" else "small").toUpperCase
   def demo3(num: Int): String = "the number is " + (if num > 100 then "large" else "small")
   def demo4(num: Int): Unit   = println(if num > 100 then "large" else "small")
   def demo5(num: Int): Unit   = if num > 100 then println("large") else println("small")
