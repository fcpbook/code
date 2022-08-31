package mocks

import mocks.Chap10.{ Account, Operation, User, Request as IntRequest }
import mocks.Chap25.*
import tinyscalautils.timing.delay

import java.util.concurrent.{ BlockingQueue, LinkedBlockingQueue }
import scala.annotation.unused
import scala.concurrent.{ ExecutionContext, Future }

object Chap26:
   val someConnection: Connection = Connection(0)
   val someRequest: Request       = Request(0)

   def successfulFuture(using ExecutionContext): Future[Int] =
      Future(delay(1)(42))

   def failedFuture(using ExecutionContext): Future[String] =
      Future(delay(2)(throw NullPointerException()))

   def makeBigPage(data: List[Data]): Page = Page(data.map(_.id).sum)

   val written: BlockingQueue[String] = LinkedBlockingQueue()
   def write(msg: String): Unit       = written.add(msg)

   def allAccounts: Map[User, Account] = Map(2 -> 1)

   def parseRequest(request: IntRequest)(using ExecutionContext): Future[User] =
      if request > 0 then Future(request - 1) else Future.failed(Exception("no user"))

   def getAccount(user: User)(using ExecutionContext): Future[Account] = Future(allAccounts(user))

   def applyOperation(account: Account, @unused op: Operation)(
       using ExecutionContext
   ): Future[Int] =
      if account > 0 then Future(account - 1) else Future.failed(Exception("bad op"))

   class PageException extends Exception

   def f1(using ExecutionContext): Future[Int]    = Future(42)
   def f2(using ExecutionContext): Future[String] = Future("foo")
   def f3(using ExecutionContext): Future[Double] = Future(1.2)
