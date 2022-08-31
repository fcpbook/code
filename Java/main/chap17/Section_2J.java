package chap17;

import static mocks.Chap17.println;

class Section_2J {
  void printB() {
    println('B');
  }

  void demo() {
    var tA = new Thread(() -> println('A'), "printerA");
    var tB = new Thread(this::printB, "printerB");
    tA.start();
    tB.start();
  }

  public static void main(String[] args) {
    new Section_2J().demo();
  }
}
