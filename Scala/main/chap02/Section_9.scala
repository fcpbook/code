package chap02

import chap02.Section_8.formatMessage
import mocks.Chap02.Writer

object Section_9:
   def demo1: String = formatMessage("hello", withNewline = false) // "hello"

   def demo2: String = formatMessage(msg = "hello", user = "Joe")
   def demo3: String = formatMessage(user = "Joe", msg = "hello")
   def demo4: String = formatMessage(user = "Joe", withNewline = false, msg = "hello")

   def demo5: String = formatMessage("Tweedledee", "Tweedledum") // which is user and which is message?
   def demo6: String = formatMessage(msg = "Tweedledee", user = "Tweedledum") // clearer

   def demo7: Writer = Writer("/var/log/app.log", true)             // what is true?
   def demo8: Writer = Writer("/var/log/app.log", autoflush = true) // clearer
