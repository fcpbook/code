package chap23

import chap23.Section_1.*
import mocks.Chap23.UserInfo
import org.scalatest.funsuite.AnyFunSuite
import tinyscalautils.control.times
import tinyscalautils.io.readLines
import tinyscalautils.threads.Executors.global
import tinyscalautils.threads.{ countDownAndWait, withThreadPoolAndWait }
import tinyscalautils.util.FastRandom

import java.nio.file.Files
import java.util.concurrent.CountDownLatch
import scala.concurrent.Future

class Section_1_Tests extends AnyFunSuite:
   test("correct bound") {
      val n = Runtime.getRuntime.availableProcessors.max(6)
      1000 times {
         withThreadPoolAndWait(global) {
            val start = CountDownLatch(n)
            val rank  = Rank()
            Future
               .traverse(1 to n) { _ =>
                  Future {
                     start.countDownAndWait()
                     rank.getRank()
                  }
               }
               .map { ranks =>
                  assert(ranks.count(_.isEmpty) == n - 5)
                  assert(ranks.flatten.sorted == (1 to 5))
               }
         }
      }
   }

   test("registration and snapshot") {
      val n        = 100
      val names    = Seq.tabulate(n)("U" + _).sorted
      val namesSet = names.toSet
      val newNames = Set.tabulate(n)("R" + _)
      1000 times {
         val regNames = FastRandom.shuffle(names ++ names)
         val users    = Users()
         withThreadPoolAndWait(global) {
            val start = CountDownLatch(n * 2)
            Future
               .traverse(regNames) { name =>
                  Future {
                     start.countDownAndWait()
                     users.register(name)
                  }
               }
               .flatMap { infos =>
                  assert(infos.count(_.isEmpty) == n)
                  assert(infos.flatMap(opt => opt.map(_.name)).sorted == names)
                  val save = CountDownLatch(n + 1)
                  val newInfos = Future.traverse(newNames) { name =>
                     Future {
                        save.countDownAndWait()
                        assert(users.register(name).nonEmpty)
                     }
                  }
                  val file = Files.createTempFile("users", ".txt")
                  file.toFile.deleteOnExit()
                  save.countDownAndWait()
                  users.saveToFile(file.toString)
                  val savedUsers = readLines(Set)(file).map { line =>
                     val a = line.split(':')
                     assert(a(1) == UserInfo(a(0)).toString)
                     a(0)
                  }
                  assert(namesSet.subsetOf(savedUsers))
                  assert(savedUsers.diff(namesSet).subsetOf(newNames))
                  newInfos
               }
         }
      }
   }
