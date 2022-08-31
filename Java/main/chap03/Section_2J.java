package chap03;

import java.util.Set;

@SuppressWarnings({"RedundantIfStatement", "BoundedWildcard", "UnnecessaryLocalVariable"})
class Section_2J {
  static boolean demo(Set<String> javaSet) {
    Set<String> set = javaSet;
    if (set.add("X")) {
      // "X" was added, and was not already in the set
      return true;
    } else {
      // "X" was already in the set, which was left unchanged
      return false;
    }
  }
}
