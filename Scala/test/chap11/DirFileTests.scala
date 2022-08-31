package chap11

import chap11.fs.*
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.FileSystemException

class DirFileTests extends AnyFunSuite:
   private val dir = Section_4.demo
   private val dir1 = Dir("Dir1")
      .mkDir("Dir3")
      .mkFile("File2")
   private val dir2 = Dir("Dir2")
      .mkFile("File3")
   private val dir3 = Dir("Dir3")
   private val rm1 = Dir("Root")
      .mkFile("File1")
      .mkFile("Dir2", "File3")
   private val rm2 = Dir("Root")
      .mkDir("Dir1", "Dir3")
      .mkFile("File1")
      .mkFile("Dir1", "File2")
   private val rm3 = Dir("Root")
      .mkDir("Dir1", "Dir3")
      .mkFile("Dir1", "File2")
      .mkFile("Dir2", "File3")
   private val rm4 = Dir("Root")
      .mkDir("Dir1", "Dir3")
      .mkFile("File1")
      .mkFile("Dir2", "File3")

   test("name, toString") {
      assert(dir.name == "Root")
      assert(dir.toString == "Root/")
   }

   test("mkString") {
      assert(dir.mkString(".") == "Root/\n.File1\n.Dir1/\n..File2\n..Dir3/\n.Dir2/\n..File3\n")
   }

   test("ls, lsFiles") {
      assert(dir.ls.toSet == Set("File1", "Dir1/", "Dir2/"))
      assert(dir.lsFiles == List("File1"))
   }

   test("cd") {
      assert(dir.cd("Dir1") === dir1)
      assert(dir.cd("Dir2") === dir2)
      assert(dir.cd("Dir1", "Dir3") === dir3)
      assert(dir1.cd("Dir3") === dir3)
   }

   test("rm") {
      assert(dir.rm("Dir1") === rm1)
      assert(dir.rm("Dir2") === rm2)
      assert(dir.rm("File1") === rm3)
      assert(dir.rm("Dir1", "File2") === rm4)
   }

   test("mkDir, mkFile") {
      val mk1 = Dir("Root")
         .mkDir("Dir1")
         .mkDir("Dir2")
         .mkDir("Dir1", "Dir3")
         .mkFile("File1")
         .mkFile("Dir1", "File2")
         .mkFile("Dir2", "File3")
      assert(dir === mk1)
   }

   test("dirCount") {
      assert(dir.dirCount == 4)
      assert(dir1.dirCount == 2)
      assert(dir2.dirCount == 1)
      assert(dir3.dirCount == 1)
      assert(rm1.dirCount == 2)
      assert(rm2.dirCount == 3)
      assert(rm3.dirCount == 4)
      assert(rm4.dirCount == 4)
   }

   test("fileCount") {
      assert(dir.fileCount == 3)
      assert(dir1.fileCount == 1)
      assert(dir2.fileCount == 1)
      assert(dir3.fileCount == 0)
      assert(rm1.fileCount == 2)
      assert(rm2.fileCount == 2)
      assert(rm3.fileCount == 2)
      assert(rm4.fileCount == 2)
   }

   test("dirExists") {
      for d <- Seq("Root", "Dir1", "Dir2", "Dir3") do assert(dir.dirExists(_.name == d))
      assert(!dir.dirExists(_.dirCount > 4))
   }

   test("fileExists") {
      for f <- Seq("File1", "File2", "File3") do assert(dir.fileExists(_.name == f))
      assert(!dir.fileExists(_.name.length > 5))
   }

   test("dirFind") {
      assert(dir.dirFind(_.name == "Dir3").map(_.name).contains("Dir3"))
      assert(dir.dirFind(_.name == "dir3").isEmpty)
   }

   test("fileFind") {
      assert(dir.fileFind(_.name == "File2").map(_.name).contains("File2"))
      assert(dir.fileFind_bad1(_.name == "File2").map(_.name).contains("File2"))
      assert(dir.fileFind_bad2(_.name == "File2").map(_.name).contains("File2"))
      assert(dir.fileFind_bad3(_.name == "File2").map(_.name).contains("File2"))
      assert(dir.fileFind(_.name == "file2").isEmpty)
      assert(dir.fileFind_bad1(_.name == "file2").isEmpty)
      assert(dir.fileFind_bad2(_.name == "file2").isEmpty)
      assert(dir.fileFind_bad3(_.name == "file2").isEmpty)

   }

   test("dirFold") {
      assert(dir.dirFold(42)(_ ^ _.name.##) == 422991)
      assert(dir1.dirFold(42)(_ ^ _.name.##) == 40)
      assert(dir2.dirFold(42)(_ ^ _.name.##) == 2130255)
      assert(dir3.dirFold(42)(_ ^ _.name.##) == 2130252)
      assert(rm1.dirFold(42)(_ ^ _.name.##) == 422989)
      assert(rm2.dirFold(42)(_ ^ _.name.##) == 2553130)
      assert(rm3.dirFold(42)(_ ^ _.name.##) == 422991)
      assert(rm4.dirFold(42)(_ ^ _.name.##) == 422991)
   }

   test("fileFold") {
      assert(dir.fileFold(42)(_ ^ _.name.##) == 67881534)
      assert(dir1.fileFold(42)(_ ^ _.name.##) == 67881532)
      assert(dir2.fileFold(42)(_ ^ _.name.##) == 67881533)
      assert(dir3.fileFold(42)(_ ^ _.name.##) == 42)
      assert(rm1.fileFold(42)(_ ^ _.name.##) == 40)
      assert(rm2.fileFold(42)(_ ^ _.name.##) == 41)
      assert(rm3.fileFold(42)(_ ^ _.name.##) == 43)
      assert(rm4.fileFold(42)(_ ^ _.name.##) == 40)
   }

   test("missing files/dirs") {
      assert(dir.rm("foo") === dir)
      assertThrows[FileSystemException](dir.cd("foo"))
      assertThrows[FileSystemException](dir.cd("File1"))
      assertThrows[FileSystemException](dir.mkDir("File1", "foo"))
      assertThrows[FileSystemException](dir.mkDir("Dir1", "File2"))
   }
