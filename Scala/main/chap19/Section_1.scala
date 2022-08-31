package chap19

object Section_1:
   def demo(): Unit =
      val tasksA: List[Runnable] = mocks.Chap19.tasksA
      val tasksB: List[Runnable] = mocks.Chap19.tasksB

      val duties: Map[Int, List[Runnable]] = Map(1 -> tasksA, 2 -> tasksB, 3 -> tasksB)

      def runTasks(id: Int): Unit = for task <- duties(id) do task.run()

      val t1 = Thread(() => runTasks(1), "T1")
      val t2 = Thread(() => runTasks(2), "T2")
      val t3 = Thread(() => runTasks(3), "T3")

      t1.start(); t2.start(); t3.start()
   end demo
