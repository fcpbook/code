package chap03;

@SuppressWarnings("ConstantConditions")
class Section_4J {
  static class WithSwitch {
    private final String arg = "-vv";
    
    final int verbosity = switch (arg) {
      case "-v" -> 1;
      case "-vv" -> 2;
      default -> 0;
    };
  }
}
