package chap26

import mocks.Chap10.{ Account, Operation, Request, User }
import mocks.Chap25.*
import mocks.Chap26.{ PageException, makeBigPage }
import tinyscalautils.threads.Executors.global

import java.util.concurrent.CompletableFuture
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try

object Section_6:
   def map[A, B](opt: Option[A], f: A => B): Option[B] = opt match
      case Some(value) => Some(f(value))
      case None        => None

   val allAccounts: Map[User, Account] = mocks.Chap26.allAccounts

   def parseRequest(request: Request): Future[User] = mocks.Chap26.parseRequest(request)
   def getAccount(user: User): Future[Account]      = Future.fromTry(Try(allAccounts(user)))
   def applyOperation(account: Account, op: Operation): Future[Int] =
      mocks.Chap26.applyOperation(account, op)

   def demo1(request: Request, op: Operation = 0): Future[Int] =
      parseRequest(request)
         .flatMap(user => getAccount(user))
         .flatMap(account => applyOperation(account, op))

   object Variant1:
      def getAccount(user: User): Future[Account] = Future.successful(allAccounts(user))

   class Demo2(pageF: Future[Page])(using ExecutionContext):
      val safePageF: Future[Page] = pageF.recover { case ex: PageException => errorPage(ex) }

   val f1: Future[Int]    = mocks.Chap26.f1
   val f2: Future[String] = mocks.Chap26.f2
   val f3: Future[Double] = mocks.Chap26.f3

   val f: Future[(Int, String, Double)] =
      f1.flatMap(n => f2.flatMap(s => f3.map(d => (n, s, d))))

   object Variant2:
      def queryDB(requests: List[mocks.Chap25.Request]): Future[Page] =
         val futures: List[Future[Data]]   = requests.map(request => Future(dbLookup(request)))
         val dataListF: Future[List[Data]] = Future.sequence(futures)
         dataListF.map(makeBigPage)

   def sequence[A](futures: List[CompletableFuture[A]]): CompletableFuture[List[A]] =
      futures match
         case Nil => CompletableFuture.completedFuture(List.empty)
         case future :: more =>
            future.thenCompose(first => sequence(more).thenApply(others => first :: others))

   def queryDB(requests: List[mocks.Chap25.Request]): Future[Page] =
      Future.traverse(requests)(request => Future(dbLookup(request))).map(makeBigPage)
