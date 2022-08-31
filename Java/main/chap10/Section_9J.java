package chap10;

import java.util.stream.IntStream;

class Section_9J {
  static IntStream demo() {
    IntStream temps = mocks.Chap10.temps();
    return temps.filter(temp -> temp > 75).map(temp -> Math.round((temp - 32) / 1.8f));
  }
}
