package chap09

import chap09.Section_1.temps

object Section_4:
   object GreaterThan90 extends Function[Int, Boolean]:
      def apply(x: Int): Boolean = x > 90

   def demo1: Option[Int] = temps.find(GreaterThan90)

   def greaterThan90(x: Int): Boolean = x > 90

   def demo2: Option[Int] = temps.find(greaterThan90)
