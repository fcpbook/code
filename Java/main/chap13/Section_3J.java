package chap13;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

class Section_3J {
  static void demo(CompletableFuture<Integer> future, IntConsumer use, Consumer<Throwable> handle) {
    future.whenComplete((value, error) -> {
      if (error == null) use.accept(value); // use value
      else handle.accept(error); // handle error
    });
  }
}
