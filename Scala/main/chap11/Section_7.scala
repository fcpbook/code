package chap11

import chap11.fs.Dir

object Section_7:
   def demo: Dir =
      Dir("Root").nav
         .mkDir("Dir2")
         .down("Dir2")
         .mkFile("File3")
         .up
         .mkDir("Dir1")
         .down("Dir1")
         .mkDir("Dir3")
         .mkFile("File2")
         .up
         .mkFile("File1")
         .dir
