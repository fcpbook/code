package chap19

import chap19.Section_3.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.availableProcessors

import scala.jdk.CollectionConverters.IterableHasAsScala

class Section_3_Tests extends AnyFunSuite:
   test("demo") {
      for n <- 1 to 2 * availableProcessors do
         val strs = Seq.tabulate(n)(_.toString)
         val m    = 100_000 / n
         val demo = Demo()
         syncForkJoin(strs)(str => demo.addStrings(m, str))
         assert(demo.shared.size == n * m)
         val all = demo.shared.getAll.asScala
         for str <- strs do assert(all.count(_ == str) == m)
   }
