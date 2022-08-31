/*
package chap14.javatrampoline;

import java.util.function.Supplier;

public interface Computation<A> {
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
*/
