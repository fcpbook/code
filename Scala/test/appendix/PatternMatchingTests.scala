package appendix

import org.scalatest.funsuite.AnyFunSuite

import java.util

class PatternMatchingTests extends AnyFunSuite:
   import PatternMatchingJ.*

   test("demo"):
      assert(demo == "122 in Phoenix")

   test("verbosity"):
      assert(parseVerbosity("") == 0)
      assert(parseVerbosity("-v") == 1)
      assert(parseVerbosity("-vv") == 2)

   test("listInfo"):
      assert(listInfo(null) == "no list")
      var list: util.List[String] = util.ArrayList()
      assert(listInfo(list) == "an empty list")
      list.add("X")
      assert(listInfo(list) == "a random access list")
      list = util.LinkedList(list)
      assert(listInfo(list) == "some other list")
