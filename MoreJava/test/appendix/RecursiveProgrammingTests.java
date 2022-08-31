package appendix;

import org.junit.jupiter.api.Test;

import static appendix.RecursiveProgramming.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecursiveProgrammingTests {
  @Test
  void testSize() {
    var tree = new Node(2,
        new Node(1, BinTree.Empty, BinTree.Empty),
        new Node(3, BinTree.Empty, BinTree.Empty));
    assertEquals(3, size(tree));
  }
}
