package chap04

import chap04.activepassive.{ functional, immutable }
import tinyscalautils.text.CharLetters.*

object Section_3:
   def demo1(ap: functional.ActivePassive[Char]): functional.ActivePassive[Char] =
      import activepassive.functional.*
      deactivate(deactivate(activateAll(ap), A), B)

   def demo2(ap: immutable.ActivePassive[Char]): immutable.ActivePassive[Char] =
      ap.activateAll().deactivate(A).deactivate(B)
