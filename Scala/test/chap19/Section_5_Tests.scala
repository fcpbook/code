package chap19

import chap19.Section_5.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.availableProcessors

class Section_5_Tests extends AnyFunSuite:
   private def runTest[A](
       mkList: () => A,
       add: (A, String) => Unit,
       size: A => Int,
       getAll: A => List[String]
   ) =
      for n <- 1 to 2 * availableProcessors do
         val list = mkList()
         val strs = Seq.tabulate(n)(_.toString)
         val m    = 100_000 / n
         syncForkJoin(strs)(str => m times add(list, str))
         assert(size(list) == n * m)
         val all = getAll(list)
         for str <- strs do assert(all.count(_ == str) == m)
   end runTest

   test("SafeStringList") {
      runTest(() => SafeStringList(), (l, s) => l.add(s), _.size, _.getAll)
   }

   test("Variant1") {
      runTest(() => Variant1.SafeStringList(), (l, s) => l.add(s), _.size, _.getAll)
   }

   test("Variant2") {
      runTest(() => Variant2.SafeStringList(), (l, s) => l.add(s), _.size, _.getAll)
   }
