package chap15;

@SuppressWarnings("UnnecessaryLocalVariable")
class Section_5J {
  static int demo() {
    var task = new Runnable() {
      public int result;

      public void run() {
        result = 42;
      }
    };

    task.run();
    int r = task.result; // 42
    return r;
  }
}
