package chap12

object Section_11:
   def findSum(target: Int, numbers: List[Int]): Option[List[Int]] =
      if target == 0 then Some(List.empty) // target is zero: trivial solution
      else
         numbers match
            case Nil => None // target is non-zero and set is empty: no solution
            case first :: others =>
               (findSum(target - first, others).map(first :: _) // first rec call succeeds
                  orElse findSum(target, others))               // otherwise, try second rec call

   def findAllSums(target: Int, numbers: List[Int]): Set[List[Int]] =
      if target == 0 then Set(List.empty)
      else
         numbers match
            case Nil => Set.empty
            case first :: others =>
               (findAllSums(target - first, others).map(first :: _)
                  union findAllSums(target, others))

   object Bad:
      // DON'T DO THIS!
      def findSum(target: Int, numbers: List[Int]): Option[List[Int]] =
         findAllSums(target, numbers).headOption

   def lazyFindAllSums(target: Int, numbers: List[Int]): LazyList[List[Int]] =
      if target == 0 then LazyList(List.empty)
      else
         numbers match
            case Nil => LazyList.empty
            case first :: others =>
               lazyFindAllSums(target - first, others).map(first :: _)
                  #::: lazyFindAllSums(target, others)

   object Good:
      def findAllSums(target: Int, numbers: List[Int]): Set[List[Int]] =
         lazyFindAllSums(target, numbers.sorted).toSet

      def findSum(target: Int, numbers: List[Int]): Option[List[Int]] =
         lazyFindAllSums(target, numbers).headOption
   end Good

   def findShortestSum(target: Int, numbers: List[Int]): Option[List[Int]] =
      lazyFindAllSums(target, numbers).minByOption(_.length)
