package appendix;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MinimizingBlockingJ {
  static ExecutorService demo(int N, int M) {
    var exec = Executors.newFixedThreadPool(N);
    var latch = new CountDownLatch(M);

    for (int id = 1; id <= M; id++) {
      exec.execute(() -> {
        latch.countDown();
        try {
          latch.await();
        } catch (InterruptedException e) { /* ignored */ Thread.currentThread().interrupt(); }
      });
    }

    return exec;
  }
}
