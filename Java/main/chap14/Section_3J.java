/*
package chap14;

import chap14.javatrampoline.Computation;

import static chap14.javatrampoline.Computation.call;
import static chap14.javatrampoline.Computation.done;

class Section_3J {
  static Computation<Boolean> isEven(int n) {
    if (n == 0) return done(true);
    else return call(() -> isOdd(n - 1));
  }

  static Computation<Boolean> isOdd(int n) {
    if (n == 0) return done(false);
    else return call(() -> isEven(n - 1));
  }

  static Boolean demo() {
    return isEven(1_000_000).result(); // true
  }
}
*/
