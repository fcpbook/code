package chap22

import chap22.Section_3.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.lang.unit
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ Execute, countDownAndWait, withThreadPoolAndWait }

import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class Section_3_Tests extends AnyFunSuite:
   private trait Box[B]:
      def get(box: B): Unit
      def set(box: B): Boolean

   private given Box[SafeBox[Unit]] with
      def get(box: SafeBox[Unit]) = box.get
      def set(box: SafeBox[Unit]) = box.set(unit)

   private given V1: Box[Variant1.SafeBox[Unit]] with
      def get(box: Variant1.SafeBox[Unit]) = box.get
      def set(box: Variant1.SafeBox[Unit]) = box.set(unit)

   private given V2: Box[Variant2.SafeBox[Unit]] with
      def get(box: Variant2.SafeBox[Unit]) = box.get
      def set(box: Variant2.SafeBox[Unit]) = box.set(unit)

   private given S5: Box[Section_5.SafeBox[Unit]] with
      def get(box: Section_5.SafeBox[Unit]) = box.get
      def set(box: Section_5.SafeBox[Unit]) = box.set(unit)

   private given A: Box[chap27.Section_1.SafeBox[Unit]] with
      def get(box: chap27.Section_1.SafeBox[Unit]) = box.get
      def set(box: chap27.Section_1.SafeBox[Unit]) = box.set(unit)

   private def testBox[A : Box](boxGen: () => A): Unit =
      val ops = summon[Box[A]]
      for threads <- Seq.iterate(1, 10)(_ * 2) do
         100 times {
            withThreadPoolAndWait(global) {
               val box   = boxGen()
               val start = CountDownLatch(threads * 2)
               threads times Execute {
                  start.countDownAndWait()
                  ops.get(box)
               }
               Future
                  .traverse(1 to threads) { _ =>
                     Future {
                        start.countDownAndWait()
                        ops.set(box)
                     }
                  }
                  .map(bools => assert(bools.count(identity) == 1))
            }
         }

   test("SafeBox") {
      testBox(() => SafeBox())
   }

   test("SafeBox, variant 1") {
      testBox(() => Variant1.SafeBox())
   }

   test("SafeBox, variant 2") {
      testBox(() => Variant2.SafeBox())
   }

   test("SafeBox, skip lock") {
      testBox(() => Section_5.SafeBox())
   }

   test("SafeBox, atomic") {
      testBox(() => chap27.Section_1.SafeBox())
   }
