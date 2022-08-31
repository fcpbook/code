package chap02

object Section_5:
   def shorten(str: String, maxLen: Int): String =
      if str.length > maxLen then str.substring(0, maxLen - 3) + "..." else str

   def demo1: String = shorten("Functional programming", 20) // "Functional progra..."

   extension (str: String) def short(maxLen: Int): String = shorten(str, maxLen)

   def demo2: String = "Functional programming".short(20) // "Functional progra..."
