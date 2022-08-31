package chap02

object Section_10:
   object Untyped:
      // DON'T DO THIS!
      def first(pair: (Any, Any)): Any = pair(0)

      // DON'T DO THIS!
      def demo1: Int    = first((1, 2)).asInstanceOf[Int] + 10                       // 11
      def demo2: String = first(("egg", "chicken")).asInstanceOf[String].toUpperCase // "EGG"

   object TypedA:
      def first[A](pair: (A, A)): A = pair(0)

      def demo1: Int    = first((1, 2))                         // has type Int
      def demo2: Int    = first((1, 2)) + 10                    // 11
      def demo3: String = first(("egg", "chicken"))             // has type String
      def demo4: String = first(("egg", "chicken")).toUpperCase // "EGG"

   object TypedType:
      def first[Type](pair: (Type, Type)): Type = pair(0)

def first[A, B](pair: (A, B)): A = pair(0)

def demo1: Int    = first((1, "chicken")) + 10
def demo2: String = first(("egg", 2)).toUpperCase
