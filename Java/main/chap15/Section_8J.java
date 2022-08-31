package chap15;

import java.util.List;

import static chap15.Section_6.Publication;
import static chap15.Section_6.Book;
import static chap15.Section_6.Magazine;

@SuppressWarnings({"unused", "BoundedWildcard"})
class Section_8J {
  static void printTitles(List<Publication> pubs) {
    for (Publication pub : pubs) System.out.println(pub.title());
  }

  static void demo1(Book book, Magazine magazine) {
    List<Publication> pubs = List.of(book, magazine);
    printTitles(pubs); // prints both titles
  }

  static void printTitlesAndAddMagazine(List<Publication> pubs) {
    for (Publication pub : pubs) System.out.println(pub.title());
    pubs.add(new Magazine("M", 1, 2));
  }

  static void printTitlesAndAddMagazine(Publication[] pubs) {
    for (Publication pub : pubs) System.out.println(pub.title());
    pubs[0] = new Magazine("N", 1, 2);
  }

  private static final Book book1 = mocks.Chap15.book1();
  private static final Book book2 = mocks.Chap15.book2();

  static void demo2() {
    Book[] books2 = new Book[]{book1, book2};
    printTitlesAndAddMagazine(books2); // throws ArrayStoreException
  }
}
