package chap02

import chap02.Section_1.abs
import mocks.Chap02.num

object Section_2:
   def dots(length: Int): String = "." * length

   def demo1: String = dots(abs(-3)) // the string "..."

   // noinspection ScalaUnnecessarySemicolon
   def demo2: String =
      num = -3;
      num = abs(num);
      dots(num); // the string "..."

   def demo3: String = (dots compose abs)(-3) // the string "..."
   def demo4: String = (abs andThen dots)(-3) // the string "..."
