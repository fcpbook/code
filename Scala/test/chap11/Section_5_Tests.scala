package chap11

import chap11.Section_5.*
import org.scalatest.funsuite.AnyFunSuite

class Section_5_Tests extends AnyFunSuite:
   private val dir = Section_4.demo.mkFile("Dir1", "Dir3", "Dir4", "abcdef")

   test("fileCount, dirCount") {
      assert(fileCount(dir) == 4)
      assert(dirCount(dir) == 5)
   }

   test("longestFilename") {
      assert(longestFilename(dir) == "abcdef")
      assert(Variant.longestFilename(dir) == "abcdef")
   }

   test("allFileNames") {
      assert(allFileNames(dir).toSet == Set("File1", "File2", "File3", "abcdef"))
   }
