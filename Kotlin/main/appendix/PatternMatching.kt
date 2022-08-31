package appendix

object PatternMatching {
  class TryPatterns(obj: Any) {
    val str: String = when (obj) {
      1 -> "One"
      2, "2" -> "Two"
      is List<*> -> "A list"
      !is String -> "Not a string"
      else -> "Unknown"
    }
  }
}
