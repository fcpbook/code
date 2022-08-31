package chap11

import chap11.fs.Dir
import org.scalactic.Equality
import org.scalactic.TripleEquals.*

import scala.compiletime.asMatchable

given Equality[Dir] with
   def areEqual(dir1: Dir, value: Any): Boolean = value.asMatchable match
      case dir2: Dir => dirEquals(dir1, dir2)
      case _         => false

private def dirEquals(dir1: Dir, dir2: Dir): Boolean =
   dir1.name == dir2.name && {
      val s1 = dir1.ls.toSet
      val s2 = dir2.ls.toSet
      s1 == s2 && {
         val d1 = s1 -- dir1.lsFiles
         val d2 = s2 -- dir2.lsFiles
         d1 == d2 && d1.forall(d => dirEquals(dir1.cd(d.init), dir2.cd(d.init)))
      }
   }
