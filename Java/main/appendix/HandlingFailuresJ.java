package appendix;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "Convert2MethodRef"})
class HandlingFailuresJ {
  static List<String> someListComputation() {
    return mocks.Appendix.someListComputation();
  }

  static int compute(List<String> list) {
    return mocks.Appendix.compute(list);
  }

  static Optional<Integer> computeOrFail(List<String> list) {
    return mocks.Appendix.computeOrFail(list);
  }

  static Optional<List<String>> lines = mocks.Appendix.lines();

  static List<String> linesOrEmpty = lines.orElse(Collections.emptyList());
  static List<String> linesOrOther = lines.orElseGet(() -> someListComputation());

  static Optional<Integer> demo1 = lines.map(list -> compute(list));           // of type Optional<Integer>
  static Optional<Integer> demo2 = lines.flatMap(list -> computeOrFail(list)); // of type Optional<Integer>
}
