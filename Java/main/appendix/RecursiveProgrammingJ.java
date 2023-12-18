/*
package appendix;

class RecursiveProgrammingJ {
  sealed public interface BinTree {
    BinTree Empty = new Empty(); // empty tree singleton
  }
  record Empty() implements BinTree {}
  record Node(int key, BinTree left, BinTree right) implements BinTree {}

  static int size(BinTree tree) {
    return switch (tree) {
      case Empty() -> 0;
      case Node(int __, BinTree left, BinTree right) -> 1 + size(left) + size(right);
    };
  }
}
*/
