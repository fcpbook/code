package chap20

trait IntQueue[Q]:
   def put(q: Q, value: Int): Unit
   def take(q: Q): Option[Int]
   def putAll(q: Q, values: Int*): Unit
   def drain(q: Q): List[Int]

extension [Q : IntQueue](q: Q)
   def put(value: Int): Unit = summon[IntQueue[Q]].put(q, value)
   def take(): Option[Int]   = summon[IntQueue[Q]].take(q)
   def putAll(values: Int*)  = summon[IntQueue[Q]].putAll(q, values*)
   def drain(): List[Int]    = summon[IntQueue[Q]].drain(q)

given priv: IntQueue[singleprivate.ConcurrentQueue[Int]] with
   import singleprivate.*

   def put(q: ConcurrentQueue[Int], value: Int): Unit      = q.put(value)
   def take(q: ConcurrentQueue[Int]): Option[Int]          = q.take()
   def putAll(q: ConcurrentQueue[Int], values: Int*): Unit = q.putAll(values*)
   def drain(q: ConcurrentQueue[Int]): List[Int]           = q.drain()
end priv

given spl: IntQueue[split.ConcurrentQueue[Int]] with
   import split.*

   def put(q: ConcurrentQueue[Int], value: Int): Unit      = q.put(value)
   def take(q: ConcurrentQueue[Int]): Option[Int]          = q.take()
   def putAll(q: ConcurrentQueue[Int], values: Int*): Unit = q.putAll(values*)
   def drain(q: ConcurrentQueue[Int]): List[Int]           = q.drain()
end spl

class PubIntQueue(nonLockDrain: Boolean = false) extends IntQueue[singlepublic.ConcurrentQueue[Int]]:
   import singlepublic.*

   def put(q: ConcurrentQueue[Int], value: Int): Unit      = q.put(value)
   def take(q: ConcurrentQueue[Int]): Option[Int]          = q.take()
   def putAll(q: ConcurrentQueue[Int], values: Int*): Unit = Section_2.putAll(q, values*)
   def drain(q: ConcurrentQueue[Int]): List[Int] =
      if nonLockDrain then Section_2.Variant.drain(q) else Section_2.drain(q)
end PubIntQueue

given pub: IntQueue[singlepublic.ConcurrentQueue[Int]] = PubIntQueue()
