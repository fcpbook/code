package chap05

import scala.util.matching.Regex

object Section_7:
   object ARGB:
      def unapply(argb: Int): (Int, Int, Int, Int) =
         var bits = argb
         val b    = bits & 0xFF
         bits >>>= 8
         val g = bits & 0xFF
         bits >>>= 8
         val r     = bits & 0xFF
         val alpha = bits >>> 8
         (alpha, r, g, b)

   object Demo1:
      val ARGB(a, r, g, b) = 0xABCDEF12

   val Phone: Regex = """(?:\+1\s)?([2-9]\d{2})[\s-]([2-9]\d{2})-?(\d{4})""".r

   def formatNumber(str: String): Option[String] = str match
      case Phone(npa, nxx, number) => Some(s"($npa) $nxx-$number")
      case _                       => None

   def demo2: Option[String] = formatNumber("603 5551234")     // Some("(603) 555-1234")
   def demo3: Option[String] = formatNumber("+1 603-555-1234") // Some("(603) 555-1234")
   def demo4: Option[String] = formatNumber("6035551234")      // None
