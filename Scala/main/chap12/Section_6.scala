package chap12

object Section_6:
   def clean(str: String): String = str.filter(_.isLetter) // remove non-letters

   object WithLists:
      def longWords(lines: List[String], min: Int): List[String] =
         lines
            .flatMap(_.split(' '))
            .map(clean)
            .filter(_.length >= min)

   object WithFold:
      def longWords(lines: List[String], min: Int): List[String] =
         lines.foldRight(List.empty[String]) { (line, words) =>
            line.split(' ').foldRight(words) { (word, moreWords) =>
               val cleanWord = clean(word)
               if cleanWord.length >= min then cleanWord :: moreWords else moreWords
            }
         }

   def longWords(lines: List[String], min: Int): List[String] =
      lines
         .to(LazyList)
         .flatMap(_.split(' '))
         .map(clean)
         .filter(_.length >= min)
         .toList
