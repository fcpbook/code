package appendix

import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in

import scala.jdk.CollectionConverters.ListHasAsScala
import scala.jdk.OptionConverters.RichOptional

class HandlingFailuresTests extends AnyFunSuite:
   import HandlingFailures.*
   import HandlingFailuresJ.*

   test("map/flatMap (Scala)") {
      assert(MapFlatMap.optStr.forall(str => str == "A" || str == "42"))
      assert(MapFlatMap.optInt.forall(n => n == 42))
   }

   test("map/flatMap (Java)") {
      assert(demo1.toScala.forall(n => n == 1))
      assert(demo2.toScala.forall(n => n == 1))
   }

   test("orElse (Scala)") {
      assert(OrElse.str in Set("a", "42", "B"))
      assert(OrElse.optStr.forall(str => str in Set("a", "42", "B")))
   }

   test("orElse (Java)") {
      val loe = linesOrEmpty.asScala
      assert(loe.isEmpty || loe == List("A"))
      val loo = linesOrOther.asScala
      assert(loo == List("A") || loo == List("B"))
   }
