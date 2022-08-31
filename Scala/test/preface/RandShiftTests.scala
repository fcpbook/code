package preface

import org.scalatest.funsuite.AnyFunSuite

import java.util as ju
import scala.util as su

class RandShiftTests extends AnyFunSuite:

   test("RandShift") {
      for f <- Seq(
           RandShift1.randShift,
           RandShift2.randShift,
           RandShift3.randShift,
           RandShift4.randShift,
           RandShift5.randShift,
         )
      do
         assertResult(List(11, 11, 13, 51, 15, 14)) {
            f(List(3, 17, 5, 23, 41, 12, 10, 5), su.Random(1))
         }
   }

   test("RandShiftJ") {
      for f <- Seq(RandShiftJ1.randShift, RandShiftJ2.randShift) do
         assertResult(ju.List.of(11, 11, 13, 51, 15, 14)) {
            f(ju.List.of(3, 17, 5, 23, 41, 12, 10, 5), ju.Random(1L))
         }
   }
