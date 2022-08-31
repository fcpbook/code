package chap11

import chap11.fs.Dir

object Section_5:
   def fileCount(dir: Dir): Int = dir.fileFold(0)((acc, _) => acc + 1)
   def dirCount(dir: Dir): Int  = dir.dirFold(0)((acc, _) => acc + 1)

   def longestFilename(dir: Dir): String = dir.fileFold("") { (longest, file) =>
      if file.name.length > longest.length then file.name else longest
   }

   object Variant:
      def longestFilename(dir: Dir): String = dir.fileFold("") { (longest, file) =>
         Seq(file.name, longest).maxBy(_.length)
      }

   def allFileNames(dir: Dir): List[String] =
      dir.dirFold(List.empty[String])((list, subdir) => subdir.lsFiles ::: list)
