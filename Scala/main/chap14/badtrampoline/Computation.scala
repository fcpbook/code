package chap14.badtrampoline

import scala.annotation.tailrec

// DON'T DO THIS!
sealed trait Computation[A]:
   @tailrec
   final def result: A = this match
      case Done(value) => value
      case Call(thunk) => thunk().result

   def map[B](function: A => B): Computation[B]
end Computation

private final case class Done[A](value: A) extends Computation[A]:
   def map[B](f: A => B): Computation[B] = Done(f(value))

private final case class Call[A](thunk: () => Computation[A]) extends Computation[A]:
   def map[B](f: A => B): Computation[B] = Call(() => Done(f(thunk().result)))
