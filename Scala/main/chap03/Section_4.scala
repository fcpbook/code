package chap03

object Section_4:
   class Demo1(arg: String):
      var verbosity = 0
      if arg == "-v" then verbosity = 1
      else if arg == "-vv" then verbosity = 2

   // noinspection TypeAnnotation
   class Demo2(arg: String):
      val verbosity = if arg == "-v" then 1 else if arg == "-vv" then 2 else 0
