package mocks

import chap24.*
import tinyscalautils.text.{ PrintingMode, println }
import tinyscalautils.timing.delay

import java.util.concurrent.Executor

object Chap24:
   def sleepTask(n: Int)(using PrintingMode): Unit =
      println(s"begin $n")
      println(delay(n.toDouble)(s"end $n"))

   trait Runner[R[_]]:
      def run[A](runner: R[A], inputs: Seq[A]): Unit
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A]

   extension [A, R[_] : Runner](runner: R[A])
      def run(inputs: Seq[A]): Unit = summon[Runner[R]].run(runner, inputs)

   object Runner:
      def apply[A, R[_] : Runner](comp: A => Unit): R[A] =
         summon[Runner[R]].newRunner(None, None)(comp)

      def apply[A, R[_] : Runner](bound: Int)(comp: A => Unit): R[A] =
         summon[Runner[R]].newRunner(None, Some(bound))(comp)

      def apply[A, R[_] : Runner](exec: Executor)(comp: A => Unit): R[A] =
         summon[Runner[R]].newRunner(Some(exec), None)(comp)

      def apply[A, R[_] : Runner](exec: Executor, bound: Int)(comp: A => Unit): R[A] =
         summon[Runner[R]].newRunner(Some(exec), Some(bound))(comp)
   end Runner

   given sequentialRunner: Runner[Section_1.Runner] with
      import Section_1.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](comp)

   given newThreadRunner: Runner[Section_2.Runner] with
      import Section_2.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](comp)

   object newThreadRunnerBad extends Runner[Section_2.Runner]:
      import Section_2.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run_bad(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](comp)

   given boundedRunner: Runner[Section_3.Runner] with
      import Section_3.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](bound.get)(comp)

   given localPoolRunner: Runner[Section_4.Runner] with
      import Section_4.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](bound.get)(comp)

   given poolRunner: Runner[Section_5.Runner] with
      import Section_5.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](exec.get)(comp)

   given boundedPoolRunner: Runner[Section_6.Runner] with
      import Section_6.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](exec.get, bound.get)(comp)

   given parRunner: Runner[Section_7.Runner] with
      import Section_7.Runner as R
      def run[A](runner: R[A], inputs: Seq[A]): Unit = runner.run(inputs)
      def newRunner[A](exec: Option[Executor], bound: Option[Int])(comp: A => Unit): R[A] =
         R[A](comp)
