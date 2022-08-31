package chap11

import chap11.fs.Dir

object Section_4:
   def demo: Dir = Dir("Root")
      .mkFile("Dir2", "File3")
      .mkDir("Dir1", "Dir3")
      .mkFile("Dir1", "File2")
      .mkFile("File1")
