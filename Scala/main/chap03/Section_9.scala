package chap03

object Section_9:
   object MutableLoads:
      trait Load:
         def weight: Int // current weight

         def reduce(): Unit // reduce load weight by one

      def reduceAll(loads: List[Load]): List[Load] =
         for load <- loads do load.reduce()
         loads
   end MutableLoads

   object ImmutableLoads:
      trait Load:
         def weight: Int   // current weight
         def reduced: Load // a reduced load, with a weight reduced by one

      def reduceAll(loads: List[Load]): List[Load] = for load <- loads yield load.reduced
   end ImmutableLoads
