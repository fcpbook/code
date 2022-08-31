package appendix;

import org.junit.jupiter.api.Test;

import java.util.List;

import static appendix.PatternMatching.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternMatchingTests {
  @Test
  void testDemo() {
    assertEquals("122 in Phoenix", demo());
  }

  @Test
  void testParseVerbosity() {
    assertEquals(1, parseVerbosity("-v"));
    assertEquals(2, parseVerbosity("-vv"));
    assertEquals(0, parseVerbosity("-vvv"));
  }

  @Test
  void testListInfo() {
    List<String> list = new java.util.ArrayList<>();
    assertEquals("no list", listInfo(null));
    assertEquals("an empty list", listInfo(list));
    list.add("X");
    assertEquals("a random access list", listInfo(list));
    list = new java.util.LinkedList<>(list);
    assertEquals("some other list", listInfo(list));
  }
}
