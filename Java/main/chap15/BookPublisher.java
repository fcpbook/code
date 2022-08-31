package chap15;

import scala.Function0;
import static chap15.Section_6.Book;

public class BookPublisher implements Function0<Book> {
  public Book apply() {
    return mocks.Chap15.book1();
  }
}
