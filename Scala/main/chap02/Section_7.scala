package chap02

object Section_7:
   def demo1(): Unit = printf("%d bottles of beer\n", 99)
   def demo2(): Unit = printf("%s: $%.2f\n", "total", 12.3456)

   def average(first: Double, others: Double*): Double = (first + others.sum) / (1 + others.size)

   def demo3: Double = average(1.0, 2.3, 4.1)
   def demo4: Double = average(10.0, 20.0)
   def demo5: Double = average(10.0)
