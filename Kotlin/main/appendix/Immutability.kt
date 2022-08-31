package appendix

object Immutability {
  @Suppress("LiftReturnOrAssignment", "CascadeIf")
  fun parseVerbosity(arg: String): Int {
    val verbosity: Int
    if (arg == "-v") verbosity = 1
    else if (arg == "-vv") verbosity = 2
    else verbosity = 0
    return verbosity
  }

  @Suppress("UnnecessaryVariable")
  fun demo(): Int {
    val readOnly: List<Int> = listOf(1, 2, 3)
    val cheating = readOnly as MutableList<Int>
    cheating[1] = 20
    val x = readOnly[1] // 20
    return x
  }

  val a: List<String> = listOf("A", "B", "C")
  val b = a.drop(1) // requires a full copy, no data sharing
}
