package appendix

object AtomicityLocking {
  class Rank1 {
    private val lock = Object()
    private var userCount = 0

    fun getRank(): Int? = synchronized(lock) {
      if (userCount < 5) {
        userCount += 1
        userCount
      } else null
    }
  }

  class Rank2 {
    private var userCount = 0

    @Synchronized
    fun getRank(): Int? =
      if (userCount < 5) {
        userCount += 1
        userCount
      } else null
  }
}
