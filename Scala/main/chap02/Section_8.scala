package chap02

object Section_8:
   def formatMessage(
       msg: String,
       user: String = "",
       withNewline: Boolean = true
   ): String =
      val sb = StringBuilder()
      if user.nonEmpty then sb.append(user).append(": ")
      sb.append(msg)
      if withNewline then sb.append("\n")
      sb.result()

   def demo1: String = formatMessage("hello")               // "hello\n"
   def demo2: String = formatMessage("hello", "Joe")        // "Joe: hello\n"
   def demo3: String = formatMessage("hello", "Joe", false) // "Joe: hello"
