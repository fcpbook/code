package chap11

import chap11.fs.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times

import java.nio.file.FileSystemException

class DirNavTests extends AnyFunSuite:
   private val dir = Section_4.demo

   test("nav") {
      var nav = dir.nav
      nav = nav.down("Dir1")
      assert(nav.lsFiles == List("File2"))
      nav = nav.rm("File2")
      assert(nav.ls == List("Dir3/"))
      nav = nav.rm("Dir3")
      assert(nav.ls.isEmpty)
      nav = nav.up
      nav = nav.rm("Dir1")
      nav = nav.rm("File1")
      assert(nav.ls == List("Dir2/"))
      nav = nav.down("Dir2")
      assert(nav.ls == List("File3"))
      nav = nav.rm("File3")
      nav = nav.up
      assert(!nav.fileExists(_ => true))
      nav = nav.rm("Dir2")
      assert(nav.dir === Dir("Root"))
   }

   test("nav deep") {
      val n    = 100
      val dirs = List.tabulate(n)(i => s"d$i")
      val dir  = Dir("R").mkDir(dirs.head, dirs.tail*)
      var nav  = dir.nav
      10 times {
         assert(nav.ls == List("d0/"))
         nav = nav.down(dirs.head, dirs.tail.head, dirs.tail.tail*)
         assert(nav.ls.isEmpty)
         nav = nav.top
      }
      assert(nav.dir === dir)
   }

   test("missing files/dirs") {
      val nav = dir.nav
      assert(nav.up.dir === dir)
      assert(nav.top.dir === dir)
      assertThrows[FileSystemException](nav.down("foo"))
      assertThrows[FileSystemException](nav.down("File1"))
      assertThrows[FileSystemException](nav.down("Dir1", "File2"))
      assertThrows[FileSystemException](nav.down("Dir1", "foo"))
   }
