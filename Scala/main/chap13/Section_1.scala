package chap13

//noinspection TypeAnnotation,DuplicatedCode
object Section_1:
   val words = List("one", "two", "three", "four")

   object Variant1:
      def search[A](values: List[A], target: A): Int = mocks.Chap13.search(values, target)

      // DON'T DO THIS!
      def between[A](values: List[A], from: A, to: A): List[A] =
         val i = search(values, from)
         val j = search(values, to)
         values.slice(i min j, (i max j) + 1)

      def demo1: List[String] = between(words, "two", "four") // List("two", "three", "four")
      def demo2: List[String] = between(words, "four", "two") // List("two", "three", "four")
      def demo3: List[String] = between(words, "two", "five") // List("one", "two")
      def demo4: List[String] = between(words, "ten", "four") // List("one", "two", "three", "four")
   end Variant1

   object Variant2:
      import scala.math.Ordering.Implicits.infixOrderingOps

      def search[A](values: List[A], target: A): Integer = mocks.Chap13.searchNull(values, target)

      def between[A](values: List[A], from: A, to: A): List[A] =
         val i = search(values, from)
         val j = search(values, to)
         values.slice(i min j, (i max j) + 1)

      def demo1: List[String] = between(words, "two", "four") // List("two", "three", "four")
      def demo2: List[String] = between(words, "four", "two") // List("two", "three", "four")
      def demo3: List[String] = between(words, "two", "five") // throws NullPointerException
      def demo4: List[String] = between(words, "ten", "four") // throws NullPointerException
   end Variant2
