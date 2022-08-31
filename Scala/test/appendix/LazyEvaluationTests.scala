package appendix

import mocks.Appendix.computed
import org.scalactic.Tolerance
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.assertions.in

import java.math.BigInteger
import java.util as ju
import scala.annotation.tailrec
import scala.jdk.CollectionConverters.IterableHasAsScala
import scala.jdk.OptionConverters.RichOptional

class LazyEvaluationTests extends AnyFunSuite with Tolerance:
   import LazyEvaluation.*
   import LazyEvaluationJ.*

   test("fill/tabulate (Scala)") {
      assert(LazyEvaluation.demo1 == List("X", "XX", "XXX", "XXXX", "XXXXX"))
      @tailrec def loop(): Boolean = LazyEvaluation.demo2.toSet.size != 1 || loop()
      assert(loop())
   }

   test("fill/tabulate (Java)") {
      assert(LazyEvaluationJ.demo1 == ju.List.of("X", "XX", "XXX", "XXXX", "XXXXX"))
      @tailrec def loop(): Boolean = LazyEvaluationJ.demo2.asScala.toSet.size != 1 || loop()
      assert(loop())
   }

   test("lazy val") {
      computed = 0
      assert(variable == 1)
      assert(computed == 1)
      assert(variable == 1)
      assert(computed == 1)
   }

   test("timeOf") {
      assert(time === 1.0 +- 0.1)
   }

   test("orElse") {
      assert(str in Set("a", "42", "B"))
      assert(optStr.toScala.forall(str => str in Set("a", "42", "B")))
   }

   test("collatz") {
      assert(collatz(BigInteger.valueOf(27)) == 111)
      val n = BigInteger("992774507105260663893249807781832616822016143650134730933270")
      assert(collatz(n) == 2632)
   }
