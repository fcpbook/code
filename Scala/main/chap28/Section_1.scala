package chap28

import scala.util.Try

object Section_1:
   trait Strategy[A, B]:
      trait Task:
         def run(): Option[B]
         def cancel(): Unit

      def apply(input: A): Task
   end Strategy

   def safe[A, B](f: A => Option[B]): A => Option[B] = x => Try(f(x)).getOrElse(None)

   class Runner[A, B](strategies: Seq[Strategy[A, B]]):
      def compute(input: A): Option[B] =
         strategies.view.flatMap(strategy => strategy(input).run()).headOption
