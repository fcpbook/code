package mocks

import chap03.Section_8.{ ImmutableDirectory, MutableDirectory }
import chap03.Section_9.{ ImmutableLoads, MutableLoads }

import scala.collection.mutable

object Chap03:
   def someMutableDirectory: MutableDirectory.Directory = mutable.Set("user 1", "user 2")

   def someOldMutableDirectory: MutableDirectory.Directory = someMutableDirectory

   def someImmutableDirectory: ImmutableDirectory.Directory = Set("user 1", "user 2")

   def someOldImmutableDirectory: ImmutableDirectory.Directory = someImmutableDirectory

   case class MutableLoad(private var load: Int) extends MutableLoads.Load:
      def weight: Int = load

      def reduce(): Unit = load -= 1

   case class ImmutableLoad(weight: Int) extends ImmutableLoads.Load:
      def reduced: ImmutableLoads.Load = ImmutableLoad(weight - 1)
