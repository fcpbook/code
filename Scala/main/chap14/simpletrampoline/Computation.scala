package chap14.simpletrampoline

import scala.annotation.tailrec
import scala.language.implicitConversions

sealed trait Computation[A]:
   @tailrec
   final def result: A = this match
      case Done(value) => value
      case Call(thunk) => thunk().result

final case class Done[A](value: A)                    extends Computation[A]
final case class Call[A](thunk: () => Computation[A]) extends Computation[A]

implicit def done[A](value: A): Computation[A]       = Done(value)
def call[A](comp: => Computation[A]): Computation[A] = Call(() => comp)
