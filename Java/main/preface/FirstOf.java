package preface;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

interface StringComputation {
  String compute();
}

@SuppressWarnings("Convert2Lambda")
class FirstOf1 {
  static String firstOf(final StringComputation comp1, final StringComputation comp2)
      throws InterruptedException {
    class Result {
      private String value = null;

      public synchronized void setValue(String str) {
        if (value == null) {
          value = str;
          notifyAll();
        }
      }

      public synchronized String getValue() throws InterruptedException {
        while (value == null)
          wait();
        return value;
      }
    }

    final Result result = new Result();
    Runnable task1 = new Runnable() {
      public void run() {
        result.setValue(comp1.compute());
      }
    };
    Runnable task2 = new Runnable() {
      public void run() {
        result.setValue(comp2.compute());
      }
    };
    new Thread(task1).start();
    new Thread(task2).start();
    return result.getValue();
  }
}

class FirstOf2 {
  static String firstOf(StringComputation comp1, StringComputation comp2, Executor threads)
      throws InterruptedException, ExecutionException {
    var result = new CompletableFuture<String>();
    result.completeAsync(comp1::compute, threads);
    result.completeAsync(comp2::compute, threads);
    return result.get();
  }
}
