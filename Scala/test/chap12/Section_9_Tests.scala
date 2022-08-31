package chap12

import chap12.Section_9.*
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.text.printout

class Section_9_Tests extends AnyFunSuite:
   test("demos") {
      assert(demo1.contains(2))
      assert(demo2.toSeq == Seq(2, 3, 4))
      assertResult("""|multiplying
                      |multiplying
                      |multiplying
                      |List(10, 20, 30)
                      |10
                      |30
                      |10
                      |""".stripMargin) {
         printout(demo3())
      }
      assertResult("""|LazyList(<not computed>)
                      |multiplying
                      |10
                      |multiplying
                      |multiplying
                      |30
                      |10
                      |""".stripMargin) {
         printout(demo4())
      }
      assertResult("""|SeqView(<not computed>)
                      |multiplying
                      |10
                      |multiplying
                      |multiplying
                      |multiplying
                      |30
                      |multiplying
                      |10
                      |""".stripMargin) {
         printout(demo5())
      }
   }
