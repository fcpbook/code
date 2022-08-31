package appendix

import mocks.Appendix.someComputation

import scala.util.Random

object LazyEvaluation:
   def demo1: List[String] = List.tabulate(5)(i => "X" * (i + 1)) // [X, XX, XXX, XXXX, XXXXX]
   def demo2: List[Int]    = List.fill(5)(Random.between(1, 11))  // 5 numbers, possibly different

   lazy val variable: Int = someComputation()
end LazyEvaluation
