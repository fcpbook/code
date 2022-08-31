package chap19

import chap19.Section_4.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.test.threads.syncForkJoin
import tinyscalautils.text.StringLetters.X
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.availableProcessors

import java.util.concurrent.atomic.LongAdder
import scala.jdk.CollectionConverters.IterableHasAsScala

class Section_4_Tests extends AnyFunSuite:
   test("Demo1") {
      for c <- 1 to availableProcessors do
         val n     = 2 * c
         val demo  = Demo1()
         val added = LongAdder()
         syncForkJoin(Seq.tabulate(n)(_.toString)) { str =>
            if demo.addStringIfCapacity(str, c) then added.increment()
         }
         assert(demo.shared.size == c)
         assert(added.intValue == c)
         assert(demo.shared.getAll.asScala.toSet.size == c)
   }

   test("Demo2") {
      for n <- 1 to 2 * availableProcessors do
         val m    = 100_000 / n
         val list = Demo2.SafeStringList()
         syncForkJoin(1 to n)(_ => m times list.add(X))
         assert(list.size == n * m)
   }
