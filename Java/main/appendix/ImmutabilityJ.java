package appendix;

import java.util.List;

class ImmutabilityJ {
  @SuppressWarnings("ConstantConditions")
  static boolean demo() {
    List<Integer> nums = List.of(1, 2, 3);
    return nums.add(4); // throws UnsupportedOperationException
  }

}
