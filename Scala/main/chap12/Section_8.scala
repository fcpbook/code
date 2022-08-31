package chap12

import chap12.Section_5.hanoi

import java.nio.file.Path

object Section_8:
   object WithLoops:
      def collatz(start: BigInt): Int =
         var count = 0
         var n     = start
         while n != 1 do
            n = if n % 2 == 0 then n / 2 else 3 * n + 1
            count += 1
         count

   def demo1: Int =
      collatz(27) // 111

   def demo2: Int =
      collatz(BigInt("992774507105260663893249807781832616822016143650134730933270")) // 2632

   object WithStreams:
      // DON'T DO THIS!
      def collatz(start: BigInt): Int =
         LazyList
            .iterate(start)(n => if n % 2 == 0 then n / 2 else 3 * n + 1)
            .takeWhile(_ != 1)
            .length

   def collatz(start: BigInt): Int =
      Iterator
         .iterate(start)(n => if n % 2 == 0 then n / 2 else 3 * n + 1)
         .takeWhile(_ != 1)
         .length

   def hanoiIterator[A](n: Int, from: A, mid: A, to: A): Iterator[(A, A)] =
      hanoi(n, from, mid, to).iterator

   // noinspection TypeAnnotation
   // val changed to def for easier testing
   def moves = hanoiIterator(100, 'L', 'M', 'R')

   def read(file: Path): List[String] = mocks.Chap12.read(file)

   object Variant1:
      def searchFiles(files: List[Path], lineTest: String => Boolean): Option[String] =
         val fileArray = files.toIndexedSeq
         var fileIndex = 0
         while fileIndex < fileArray.length do
            val lineArray = read(fileArray(fileIndex)).toIndexedSeq
            var lineIndex = 0
            while lineIndex < lineArray.length do
               val line = lineArray(lineIndex)
               if lineTest(line) then return Some(line)
               lineIndex += 1
            end while
            fileIndex += 1
         end while
         None

   object Variant2:
      // DON'T DO THIS!
      def searchFiles(files: List[Path], lineTest: String => Boolean): Option[String] =
         files.flatMap(read).find(lineTest)

   def searchFiles(files: List[Path], lineTest: String => Boolean): Option[String] =
      files.iterator.flatMap(read).find(lineTest)
