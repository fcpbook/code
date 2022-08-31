package chap11.fs

private sealed trait Node:
   def name: String

   def mkString(sep: String): String = mkString(sep, StringBuilder(), "").result()
   private[fs] def mkString(sep: String, builder: StringBuilder, prefix: String): StringBuilder

   private[fs] def cdPath(path: List[String]): Dir
   private[fs] def mkPath(path: List[String], filename: Option[String]): Node
   private[fs] def rmPath(path: List[String]): Node

   private[fs] def dirCount: Int
   private[fs] def dirExists(test: Dir => Boolean): Boolean
   private[fs] def dirFold[A](init: A)(f: (A, Dir) => A): A
   private[fs] def dirFind(test: Dir => Boolean): Option[Dir]

   private[fs] def fileCount: Int
   private[fs] def fileExists(test: File => Boolean): Boolean
   private[fs] def fileFold[A](init: A)(f: (A, File) => A): A
   private[fs] def fileFind(test: File => Boolean): Option[File]
end Node

//noinspection IfElseToFilterdOption
final class Dir private(val name: String, nodes: List[Node]) extends Node:
   // helper method to update children
   private[fs] def removeByName(name: String): Option[(Node, List[Node])] =
      nodes.partition(_.name == name) : @unchecked match
         case (Nil, _)              => None
         case (List(found), others) => Some((found, others))

   // String representation
   override def toString: String = name + "/"

   private[fs] def mkString(sep: String, builder: StringBuilder, prefix: String): StringBuilder =
      builder ++= prefix ++= name ++= "/\n"
      val newPrefix = prefix + sep
      nodes.foreach(_.mkString(sep, builder, newPrefix))
      builder

   def ls: List[String] = nodes.map(_.toString)

   def lsFiles: List[String] = nodes.flatMap {
      case file: File => List(file.toString)
      case _ => Nil
   }

   // Down-only navigation
   private[fs] def cdPath(path: List[String]): Dir = path match
      case Nil => this
      case dirname :: more =>
         nodes
            .find(_.name == dirname)
            .getOrElse(throw FileSystemException(dirname, "cannot change: no such dir"))
            .cdPath(more)

   def cd(name: String, names: String*): Dir = cdPath(name :: names.toList)

   // Construction
   private[fs] def mkPath(path: List[String], filename: Option[String]): Dir = path match
      case Nil =>
         filename match
            case None => this
            case Some(name) =>
               if nodes.exists(_.name == name) then
                  throw FileSystemException(name, "cannot create file: node exists")
               else Dir(this.name, File(name) :: nodes)

      case dirname :: more =>
         removeByName(dirname) match
            case None => Dir(this.name, Dir(dirname).mkPath(more, filename) :: nodes)
            case Some((node, otherNodes)) =>
               Dir(this.name, node.mkPath(more, filename) :: otherNodes)
   end mkPath
   
   private[fs] def rmPath(path: List[String]): Dir = (path : @unchecked) match
      case nodename :: more =>
         removeByName(nodename) match
            case None => this
            case Some((node, otherNodes)) =>
               if more.isEmpty then Dir(this.name, otherNodes)
               else Dir(this.name, node.rmPath(more) :: otherNodes)

   def mkFile(name: String, names: String*): Dir =
      val allNames = name :: names.toList
      mkPath(allNames.init, Some(allNames.last))

   def mkDir(name: String, names: String*): Dir = mkPath(name :: names.toList, None)

   def rm(name: String, names: String*): Dir = rmPath(name :: names.toList)

   // Traversal
   def dirCount: Int = nodes.foldLeft(1)(_ + _.dirCount)

   def dirExists(test: Dir => Boolean): Boolean = test(this) || nodes.exists(_.dirExists(test))

   def dirFold[A](init: A)(f: (A, Dir) => A): A =
      nodes.foldLeft(f(init, this))((acc, node) => node.dirFold(acc)(f))

   def dirFind(test: Dir => Boolean): Option[Dir] =
      if test(this) then Some(this) else nodes.view.flatMap(_.dirFind(test)).headOption

   def fileCount: Int = nodes.foldLeft(0)(_ + _.fileCount)

   def fileExists(test: File => Boolean): Boolean = nodes.exists(_.fileExists(test))

   def fileFold[A](init: A)(f: (A, File) => A): A =
      nodes.foldLeft(init)((acc, node) => node.fileFold(acc)(f))

   def fileFind(test: File => Boolean): Option[File] =
      nodes.view.flatMap(_.fileFind(test)).headOption

   // Switch to zipper
   def nav: DirNav = DirNav(this, List.empty)

   // Bad find implementations
   def fileFind_bad1(test: File => Boolean): Option[File] =
      fileFold(Option.empty[File]) { (found, file) =>
         found.orElse(if test(file) then Some(file) else None)
      }

   def fileFind_bad2(test: File => Boolean): Option[File] =
      nodes.find(_.fileExists(test)).map(_.fileFind(test).get)

   def fileFind_bad3(test: File => Boolean): Option[File] =
      nodes.view.map(_.fileFind(test)).find(_.nonEmpty).flatten
end Dir

//noinspection IfElseToFilterdOption
final class File(val name: String) extends Node:
   override def toString: String = name

   private[fs] def mkString(sep: String, builder: StringBuilder, prefix: String): StringBuilder =
      builder ++= prefix ++= name += '\n'

   private[fs] def cdPath(path: List[String]): Dir =
      throw FileSystemException(name, "cannot change: not a directory")
   private[fs] def mkPath(path: List[String], filename: Option[String]) =
      throw FileSystemException(name, "cannot create dir: file exists")
   private[fs] def rmPath(path: List[String]) = this

   private[fs] def dirCount: Int                              = 0
   private[fs] def dirExists(test: Dir => Boolean): Boolean   = false
   private[fs] def dirFold[A](init: A)(f: (A, Dir) => A): A   = init
   private[fs] def dirFind(test: Dir => Boolean): Option[Dir] = None

   private[fs] def fileCount: Int                             = 1
   private[fs] def fileExists(test: File => Boolean): Boolean = test(this)
   private[fs] def fileFold[A](init: A)(f: (A, File) => A): A = f(init, this)
   private[fs] def fileFind(test: File => Boolean): Option[File] =
      if test(this) then Some(this) else None // or: Some(this).filter(test)
end File

object Dir:
   def apply(name: String): Dir = Dir(name, List.empty)

   private[fs] def apply(name: String, nodes: List[Node]): Dir = new Dir(name, nodes)
end Dir

private object FileSystemException:
   import java.nio.file.FileSystemException

   def apply(name: String, message: String): FileSystemException =
      new FileSystemException(name, null, message)
end FileSystemException
