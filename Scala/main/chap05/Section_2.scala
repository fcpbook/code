package chap05

//noinspection ScalaUnusedSymbol,TypeAnnotation
object Section_2:
   val pair: (String, Int) = ("foo", 42)

   def demo1: String =
      pair match
         case (str, 0) => "no match because the number in pair is not zero"
         case (str, n) => """str is the string "foo", n is the integer 42"""

   object Demo2:
      val (str, n) = pair // str is the string "foo", n is the integer 42

   object Demo3:
      case class TempRecord(city: String, temperature: Int)

      val rec = TempRecord("Phoenix", 122)

      def demo1: String = rec.city        // "Phoenix"
      def demo2: Int    = rec.temperature // 122

      val TempRecord(name, temp) = rec // name is "Phoenix", temp is 122
