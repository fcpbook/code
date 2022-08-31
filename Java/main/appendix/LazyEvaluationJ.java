package appendix;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static mocks.Appendix.*;

@SuppressWarnings({"Convert2MethodRef", "OptionalUsedAsFieldOrParameterType"})
class LazyEvaluationJ {
  static RandomGenerator random = RandomGenerator.getDefault();

  static List<String> demo1 = IntStream.range(1, 6).mapToObj("X"::repeat).toList(); // [X, XX, XXX, XXXX, XXXXX]

  static List<Integer> demo2() {
    return random.ints(1, 11).limit(5).boxed().toList(); // 5 numbers, possibly different
  }

  @SuppressWarnings("CodeBlock2Expr")
  static double time = timeOf(() -> {
    computeSomething();
  });

  static Optional<String> maybeString = mocks.Appendix.maybeStringJava();

  static String str = maybeString.orElseGet(() -> someStringComputation());
  static Optional<String> optStr = maybeString.or(() -> maybeOtherStringJava());

  @SuppressWarnings("SimplifyStreamApiCallChains")
  static int collatz(BigInteger start) {
    return (int) Stream.iterate(start, n ->
            n.mod(BigInteger.TWO).equals(BigInteger.ZERO) ? n.divide(BigInteger.TWO)
                : n.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE))
        .takeWhile(n -> !n.equals(BigInteger.ONE))
        .count();
  }

  /* Note that collatz would be more idiomatic in Java as:
  static int collatz(BigInteger start) {
    return (int) Stream.iterate(start, x -> !x.equals(BigInteger.ONE), n ->
        n.mod(BigInteger.TWO).equals(BigInteger.ZERO) ? n.divide(BigInteger.TWO)
            : n.multiply(BigInteger.valueOf(3)).add(BigInteger.ONE)
    ).count();
  }
  */
}
