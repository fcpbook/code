package chap15

object Aside:
   class T:
      def service1: Int              = mocks.Chap15.service1
      def service2(str: String): Int = mocks.Chap15.service2(str)
      def service3(n: Int): String   = mocks.Chap15.service3(n)

   object Inheritance:
      class S extends T:
         override def service3(n: Int): String = super.service3(n + service1)
         def service4: Double                  = mocks.Chap15.service4

   object Composition:
      class S:
         private val underlying: T = T()
         export underlying.service1
         def service3(n: Int): String = underlying.service3(n + service1)
         def service4: Double         = mocks.Chap15.service4
