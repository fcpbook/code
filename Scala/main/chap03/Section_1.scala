package chap03

object Section_1:
   var prompt = "> "

   def format(msg: String): String = prompt + msg

   val demo1: String = format("command") // the string "> command"

   val demo2: String =
      prompt = "% "
      format("command") // the string "% command"

   var lastID = 0

   def uniqueName(prefix: String): String =
      lastID += 1
      prefix + lastID

   val demo3: String = uniqueName("user-") // "user-1"
   val demo4: String = uniqueName("user-") // "user-2"
