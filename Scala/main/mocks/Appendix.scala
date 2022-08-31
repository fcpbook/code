package mocks

import tinyscalautils.timing.sleep
import tinyscalautils.util.FastRandom

import java.net.URL
import java.util as ju
import java.util.concurrent.{ ConcurrentLinkedQueue, CountDownLatch }
import scala.jdk.CollectionConverters.SeqHasAsJava
import scala.jdk.OptionConverters.RichOption

object Appendix:
   var computed = 0

   def someComputation(): Int =
      computed += 1
      computed

   def maybeString(): Option[String] =
      Some(FastRandom.nextInt(3)).filter(_ > 0).map(n => if n == 1 then "a" else "42")

   def someStringComputation: String = "B"

   def maybeOtherString(): Option[String] = Option.when(FastRandom.nextBoolean())("B")

   val threads: ConcurrentLinkedQueue[Thread] = ConcurrentLinkedQueue()

   type Socket = CountDownLatch

   def handleConnection(socket: Socket): Unit =
      threads.add(Thread.currentThread)
      socket.countDown()

   val urls_java: ju.List[URL] = Chap21.urls.asJava

   val tempsList: ju.List[Integer] = ju.List.of(88, 91, 78, 69, 100, 98, 70)
   val tempsArray: Array[Int]      = Array(88, 91, 78, 69, 100, 98, 70)

   val strings: ju.List[String] = ju.List.of("42  32", "<no data> 72 100")

   def computeSomething(): Unit = sleep(1.0)

   def timeOf(task: Runnable): Double = chap12.Section_3.timeOf(task.run())

   def maybeStringJava(): ju.Optional[String]      = maybeString().toJava
   def maybeOtherStringJava(): ju.Optional[String] = maybeOtherString().toJava

   def someListComputation(): ju.List[String] = ju.List.of("B")

   def compute(list: ju.List[String]): Int = list.size

   def computeOrFail(list: ju.List[String]): ju.Optional[Integer] =
      if FastRandom.nextBoolean() then ju.Optional.of(list.size) else ju.Optional.empty()

   def lines(): ju.Optional[ju.List[String]] =
      if FastRandom.nextBoolean() then ju.Optional.of(ju.List.of("A")) else ju.Optional.empty()
