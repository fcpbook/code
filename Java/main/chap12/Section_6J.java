package chap12;

import java.util.List;
import static chap12.Section_6.clean;

@SuppressWarnings("Convert2MethodRef")
class Section_6J {
  static List<String> longWords(List<String> lines, int min) {
    return lines.stream()
        .flatMap(line -> line.replace(' ', '\n').lines())
        .map(word -> clean(word))
        .filter(word -> word.length() >= min)
        .toList();
  }
}
