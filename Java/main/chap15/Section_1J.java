package chap15;

import java.util.Set;
import static chap15.Section_6.Book;

class Section_1J {
  static class Demo {
    static int added = 0;

    static void demo(Book book) {
      Set<Book> books = mocks.Chap15.books();
      if (books.add(book))
        added += 1;
    }
  }
}
