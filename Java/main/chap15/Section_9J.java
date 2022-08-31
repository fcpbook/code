package chap15;

import scala.Function0;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static chap15.Section_6.*;

class Section_9J {
  static <A extends Publication> void printTitles(Set<A> pubs) {
    for (Publication pub : pubs) System.out.println(pub.title());
  }

  static void addMagazine(Set<? super Magazine> pubs) {
    pubs.add(new Magazine("M", 1, 10));
  }

  static <A extends Publication & Comparable<A>> void printTitlesInOrder(Set<A> pubs) {
    for (Publication pub : new TreeSet<>(pubs)) System.out.println(pub.title());
  }

  static class NonVariant {
    static <A> List<A> runInParallel(List<Function0<A>> tasks) {
      return tasks.parallelStream().map(Function0::apply).toList();
    }
  }

  public static class SemiVariant {
    static <A> List<A> runInParallel(List<? extends Function0<A>> tasks) {
      return tasks.parallelStream().map(Function0::apply).toList();
    }
  }

  static <A> List<A> runInParallel(List<? extends Function0<? extends A>> tasks) {
    return tasks.parallelStream().map(Function0::apply).collect(Collectors.toList());
  }
}
