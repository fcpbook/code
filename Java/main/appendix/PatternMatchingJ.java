package appendix;

class PatternMatchingJ {
  static int parseVerbosity(String arg) {
    return switch (arg) {
      case "-v" -> 1;
      case "-vv" -> 2;
      default -> 0;
    };
  }
}
