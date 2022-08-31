package chap04

import chap04.activepassive.{ functional, mutable1 }
import tinyscalautils.text.CharLetters.*

object Section_2:
   def demo1(ap: mutable1.ActivePassive[Char]): Unit =
      ap.activateAll()
      ap.deactivate(A)
      ap.deactivate(B)

   def demo2(ap: functional.ActivePassive[Char]): functional.ActivePassive[Char] =
      import activepassive.functional.*
      deactivate(deactivate(activateAll(ap), A), B)
