package chap05

import java.io.IOException

// noinspection TypeAnnotation,ScalaUnusedSymbol
object Section_1:
   class Demo1(arg: String):
      val verbosity = if arg == "-v" then 1 else if arg == "-vv" then 2 else 0

   class Demo2(arg: String):
      val verbosity = arg match
         case "-v"  => 1
         case "-vv" => 2
         case _     => 0

   def demo3(arg: String): String =
      arg match
         case "--"                                 => "end of options"
         case longOpt if longOpt.startsWith("--")  => "long option"
         case shortOpt if shortOpt.startsWith("-") => "short option"
         case plain                                => "plain argument"

   def demo4(code: => Any): String =
      try code.toString
      catch
         case _: IOException           => "an I/O exception"
         case e: IllegalStateException => "can refer to the exception, e.g., e.getMessage"
         case _: Exception             => "some other exception"
