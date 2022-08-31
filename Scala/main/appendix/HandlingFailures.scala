package appendix

object HandlingFailures:
   object MapFlatMap:
      val maybeString: Option[String] = mocks.Appendix.maybeString()

      val optStr: Option[String] = maybeString.map(_.toUpperCase)
      val optInt: Option[Int]    = maybeString.flatMap(_.toIntOption)

   object OrElse:
      def someStringComputation: String    = mocks.Appendix.someStringComputation
      val maybeString: Option[String]      = mocks.Appendix.maybeString()
      def maybeOtherString: Option[String] = mocks.Appendix.maybeOtherString()

      val str: String            = maybeString.getOrElse(someStringComputation)
      val optStr: Option[String] = maybeString.orElse(maybeOtherString)
end HandlingFailures
