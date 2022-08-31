package appendix;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

import static java.lang.Math.round;

@SuppressWarnings("Convert2MethodRef")
class HigherOrderFunctionsJ {
  static <A> Predicate<A> negate(Predicate<A> f) {
    return x -> !f.test(x);
  }

  static class Math {
    public boolean pos(int x) {
      return x > 0;
    }
  }
  static Math m = new Math();

  static class Neg1 {
    static Predicate<Integer> neg = negate(x -> m.pos(x));
  }

  static class Neg2 {
    static Predicate<Integer> neg = negate(m::pos);
  }

  static Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());

  static ToIntFunction<String> len = (String str) -> str.length();
  // Object len = (String str) -> str.length(); // rejected by the compiler
  // var len = (String str) -> str.length(); // rejected by the compiler

  static class ListMap {
    static List<Integer> temps = mocks.Appendix.tempsList();

    static List<Integer> celsius = temps.stream().map(temp -> round((temp - 32) / 1.8f)).toList();
  }

  static class ArrayMap {
    static int[] temps = mocks.Appendix.tempsArray();

    static int[] celsius = Arrays.stream(temps).map(temp -> round((temp - 32) / 1.8f)).toArray();
  }

  static Optional<Integer> parse(String str) {
    try {
      return Optional.of(Integer.valueOf(str));
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }

  static List<String> strings = mocks.Appendix.strings();
  static Pattern SPACES = Pattern.compile("\\s+");

  static List<Integer> celsius = strings.stream()
      .flatMap(str -> Arrays.stream(SPACES.split(str)))
      .flatMap(str -> parse(str).stream())
      .map(temp -> round((temp - 32) / 1.8f))
      .toList();

  static <A, B> Function<A, B> memo(Function<A, B> f) {
    Map<A, B> store = new HashMap<>();
    return x -> store.computeIfAbsent(x, f);
  }

  @SuppressWarnings("ConstantConditions")
  static class SingleCannotCompile {
    static <A,B> Function<A,B> single(Function<A,B> f) {
      var called = false;
      return x -> {
        if (called) throw new IllegalStateException();
        // called = true; // rejected at compile time
        return f.apply(x);
      };
    }
  }

  static <A, B> Function<A, B> single(Function<A, B> f) {
    return new Function<>() {
      private boolean called = false;

      public B apply(A x) {
        if (called) throw new IllegalStateException();
        called = true;
        return f.apply(x);
      }
    };
  }
}
