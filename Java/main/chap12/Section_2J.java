package chap12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Section_2J {
  static List<Integer> demo() {
    var rand = new Random(42);
    List<Integer> numbers = new ArrayList<>(Collections.nCopies(5, 0));
    Collections.fill(numbers, rand.nextInt(100));
    return numbers;
  }
}
