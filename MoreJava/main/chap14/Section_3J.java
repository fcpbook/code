package chap14;

import java.util.function.Supplier;

import static chap14.Section_3J.Computation.call;
import static chap14.Section_3J.Computation.done;

class Section_3J {
  interface Computation<A> {
    A result();

    static <T> Computation<T> done(T value) {
      return new Done<>(value);
    }

    static <T> Computation<T> call(Supplier<Computation<T>> thunk) {
      return new Call<>(thunk);
    }
  }

  record Done<A>(A value) implements Computation<A> {
    public A result() {
      return value;
    }
  }

  record Call<A>(Supplier<Computation<A>> thunk) implements Computation<A> {
    public A result() {
      Computation<A> calc = this;
      while (calc instanceof Call<A> call)
        calc = call.thunk().get();
      return calc.result();
    }
  }

  static Computation<Boolean> isEven(int n) {
    if (n == 0) return done(true);
    else return call(() -> isOdd(n - 1));
  }

  static Computation<Boolean> isOdd(int n) {
    if (n == 0) return done(false);
    else return call(() -> isEven(n - 1));
  }

  static Boolean demo() {
    return isEven(1_000_000).result(); // true
  }
}
