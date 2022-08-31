package preface;

import java.util.List;
import java.util.Random;

class RandShiftJ1 {
  static List<Integer> randShift(List<Integer> nums, Random rand) {
    var shiftedNums = new java.util.ArrayList<Integer>(nums.size());
    for (int num : nums) {
      int shifted = num + rand.nextInt(-10, 11);
      if (shifted > 0) shiftedNums.add(shifted);
    }
    return shiftedNums;
  }

}

class RandShiftJ2 {
  static List<Integer> randShift(List<Integer> nums, Random rand) {
    return nums.stream()
        .map(num -> num + rand.nextInt(-10, 11))
        .filter(shifted -> shifted > 0)
        .toList();
  }
}
