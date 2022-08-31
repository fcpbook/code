package chap24

import java.util.concurrent.Executor

trait Runner[R[_]]:
   def run[A](runner: R[A], inputs: Seq[A]): Unit
   def addInput[A](runner: R[A], input: A): Boolean
   def newRunner[A](exec: Executor, comp: A => Unit): R[A]

extension [A, R[_] : Runner](runner: R[A])
   def run(inputs: Seq[A]): Unit   = summon[Runner[R]].run(runner, inputs)
   def addInput(input: A): Boolean = summon[Runner[R]].addInput(runner, input)

given async1Runner: Runner[Section_8.SingleCondition.Runner] with
   import Section_8.SingleCondition.Runner as R
   def run[A](runner: R[A], inputs: Seq[A]): Unit          = runner.run(inputs)
   def addInput[A](runner: R[A], input: A): Boolean        = runner.addInput(input)
   def newRunner[A](exec: Executor, comp: A => Unit): R[A] = R(exec)(comp)

given async2Runner: Runner[Section_8.TwoConditions.Runner] with
   import Section_8.TwoConditions.Runner as R
   def run[A](runner: R[A], inputs: Seq[A]): Unit          = runner.run(inputs)
   def addInput[A](runner: R[A], input: A): Boolean        = runner.addInput(input)
   def newRunner[A](exec: Executor, comp: A => Unit): R[A] = R(exec)(comp)

given async3Runner: Runner[Section_9.Runner] with
   import Section_9.Runner as R
   def run[A](runner: R[A], inputs: Seq[A]): Unit          = runner.run(inputs)
   def addInput[A](runner: R[A], input: A): Boolean        = runner.addInput(input)
   def newRunner[A](exec: Executor, comp: A => Unit): R[A] = R(exec)(comp)
