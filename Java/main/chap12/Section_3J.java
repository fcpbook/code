package chap12;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

class Section_3J {
  static <A> void writeToFile(Path file, Iterable<A> values) throws IOException {
    try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
      for (var item : values)
        out.writeObject(item);
    }
  }
}
