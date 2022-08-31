package chap05

object Aside:
   type Stooge = "Larry" | "Curly" | "Moe"
   type Digit  = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9

   type StoogeAndDigit = (Stooge, Digit)

   type StoogeLink = (Stooge, Stooge, Boolean)

   enum StoogeOrDigit:
      case S(stooge: Stooge)
      case D(digit: Digit)

   enum Number:
      case Zero
      case Pos(digit: Digit)
      case Neg(digit: Digit)

   type OptionalStooge = Option[Stooge]

   type OptionalStoogeAndDigit = (Option[Stooge], Digit)

   enum StoogeAndDigitOrDigit:
      case SD(stooge: Stooge, digit: Digit)
      case D(digit: Digit)

   enum IntList:
      case Nil
      case Cons(head: Int, tail: IntList)
