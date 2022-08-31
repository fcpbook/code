package chap23

trait IntQueue[Q]:
   def newQueue(capacity: Int): Q
   def put(q: Q, value: Int): Unit
   def take(q: Q): Int

extension [Q : IntQueue](q: Q)
   def put(value: Int): Unit = summon[IntQueue[Q]].put(q, value)
   def take(): Int           = summon[IntQueue[Q]].take(q)

given semQ: IntQueue[Section_3.BoundedQueue[Int]] with
   import Section_3.BoundedQueue

   def newQueue(capacity: Int): BoundedQueue[Int]  = BoundedQueue(capacity)
   def put(q: BoundedQueue[Int], value: Int): Unit = q.put(value)
   def take(q: BoundedQueue[Int]): Int             = q.take()

given waitQ: IntQueue[Section_4.WaitNotifyAll.BoundedQueue[Int]] with
   import Section_4.WaitNotifyAll.BoundedQueue

   def newQueue(capacity: Int): BoundedQueue[Int]  = BoundedQueue(capacity)
   def put(q: BoundedQueue[Int], value: Int): Unit = q.put(value)
   def take(q: BoundedQueue[Int]): Int             = q.take()

given condQ: IntQueue[Section_4.BoundedQueue[Int]] with
   import Section_4.BoundedQueue

   def newQueue(capacity: Int): BoundedQueue[Int]  = BoundedQueue(capacity)
   def put(q: BoundedQueue[Int], value: Int): Unit = q.put(value)
   def take(q: BoundedQueue[Int]): Int             = q.take()
