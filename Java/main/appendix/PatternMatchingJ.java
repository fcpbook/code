package appendix;

import java.util.List;
import java.util.RandomAccess;

class PatternMatchingJ {
  static int parseVerbosity(String arg) {
    return switch (arg) {
      case "-v" -> 1;
      case "-vv" -> 2;
      default -> 0;
    };
  }

  static <A> String listInfo(List<A> list) {
    return switch (list) {
      case null -> "no list";
      case List<A> empty when empty.isEmpty() -> "an empty list";
      case RandomAccess seq -> "a random access list";
      default -> "some other list";
    };
  }

  record TempRecord(String city, int temperature) {
  }

  static String demo() {
    TempRecord rec = new TempRecord("Phoenix", 122);
    String str = switch (rec) {
      case TempRecord(String city,int temp) -> "%d in %s".formatted(temp, city);
    };

    return str;
  }
}
