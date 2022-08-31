package chap14.trampoline

import scala.annotation.tailrec
import scala.language.implicitConversions

sealed trait Computation[A]:
   @tailrec
   final def result: A = this match
      case Done(value) => value
      case Call(thunk) => thunk().result
      case FlatMap(f, arg) => arg match
            // these cases evaluate arg.flatMap(f):
            case Done(v)          => f(v).result
            case Call(thunk)      => thunk().flatMap(f).result
            case FlatMap(g, arg2) => arg2.flatMap(x => g(x).flatMap(f)).result

   def flatMap[B](f: A => Computation[B]): Computation[B] = FlatMap(f, this)

   // def map[B](f: A => B): Computation[B] = flatMap(f andThen done)
   def map[B](f: A => B): Computation[B] = flatMap(x => Done(f(x)))
end Computation

private final case class Done[A](value: A)                    extends Computation[A]
private final case class Call[A](thunk: () => Computation[A]) extends Computation[A]

private final case class FlatMap[A, B](f: A => Computation[B], arg: Computation[A])
    extends Computation[B]

object Computation:
   implicit def done[A](v: A): Computation[A]           = Done(v)
   def call[A](comp: => Computation[A]): Computation[A] = Call(() => comp)
