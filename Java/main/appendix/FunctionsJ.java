package appendix;

import java.util.Arrays;
import java.util.List;

class FunctionsJ {
  static <A> List<A> firstLast(List<A> list) {
    return List.of(list.get(0), list.get(list.size() - 1));
  }

  static double average(double first, double... others) {
    return (first + Arrays.stream(others).sum()) / (1 + others.length);
  }

  static double demo1 = average(1.0, 2.3, 4.1);
  static double demo2 = average(10.0, 20.0);
}
