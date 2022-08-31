package chap11.fs

import scala.annotation.tailrec

final class DirNav private[fs] (val dir: Dir, steps: List[(String, List[Node])]):
   // String representation
   override def toString: String = dir.toString
   export dir.{ mkString, ls, lsFiles }

   // noinspection ScalaUnusedSymbol
   // Up-and-down navigation
   def down(dirname: String): DirNav =
      dir.removeByName(dirname) match
         case None =>
            throw FileSystemException(dirname, "cannot change: no such directory")
         case Some((file: File, _)) =>
            throw FileSystemException(dirname, "cannot change: not a directory")
         case Some((subdir: Dir, otherNodes)) =>
            DirNav(subdir, (dir.name, otherNodes) :: steps)

   def down(dirname1: String, dirname2: String, dirnames: String*): DirNav =
      (dirname1 :: dirname2 :: dirnames.toList).foldLeft(this)(_.down(_))

   def up: DirNav = steps match
      case Nil                   => this
      case (name, nodes) :: more => DirNav(Dir(name, dir :: nodes), more)

   @tailrec
   def top: DirNav = if steps.isEmpty then this else up.top

   // Construction
   def mkFile(name: String, names: String*): DirNav =
      DirNav(dir.mkFile(name, names*), steps)

   def mkDir(name: String, names: String*): DirNav =
      DirNav(dir.mkDir(name, names*), steps)

   def rm(name: String, names: String*): DirNav =
      DirNav(dir.rm(name, names*), steps)

   // Traversal
   export dir.{ dirCount, dirExists, dirFold, dirFind, fileCount, fileExists, fileFold, fileFind }
end DirNav
