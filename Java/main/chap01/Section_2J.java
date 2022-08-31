package chap01;

import java.util.List;

@SuppressWarnings({"UtilityClassWithoutPrivateConstructor", "unused"})
class Section_2J {
  static String firstString1(List<String> strings) {
    return strings.get(0);
  }

  static String firstString2(List<String> strings) {
    return strings.remove(0);
  }
}
