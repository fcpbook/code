package appendix;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import static mocks.Appendix.handleConnection;
import static mocks.Chap21.distinctWordsCount;

class ThreadPoolsJ {
  static void demo(ExecutorService exec, CountDownLatch socket) {
    exec.execute(() -> handleConnection(socket));
  }

  @SuppressWarnings("Convert2MethodRef")
  static class Demo {
    static List<URL> urls = mocks.Appendix.urls_java();
    static IntStream counts = urls.parallelStream().mapToInt(url -> distinctWordsCount(url));
  }

  public static void main(String[] args) {
    System.out.println(Demo.counts.max());
  }
}
