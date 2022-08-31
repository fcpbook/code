package chap10

import mocks.Chap10.{ Account, Operation, Request, User }

//noinspection ScalaUnusedSymbol
object Section_3:
   def demo1: List[List[Int]] = List(1, 2, 3).map(x => List(x, x, x))
   // List(List(1, 1, 1), List(2, 2, 2), List(3, 3, 3))

   def demo2: List[Int] = List(1, 2, 3).flatMap(x => List(x, x, x))
   // List(1, 1, 1, 2, 2, 2, 3, 3, 3)

   def map[A, B](list: List[A], f: A => B): List[B] = list.flatMap(x => List(f(x)))

   def filter[A](list: List[A], test: A => Boolean): List[A] =
      list.flatMap(x => if test(x) then List(x) else List.empty)

   object NoOption:
      def parseRequest(request: Request): User = mocks.Chap10.parseRequest(request)

      def getAccount(user: User): Account = mocks.Chap10.getAccount(user)

      def applyOperation(account: Account, op: Operation): Int =
         mocks.Chap10.applyOperation(account, op)

      def demo(request: Request, op: Operation): Int =
         applyOperation(getAccount(parseRequest(request)), op)
   end NoOption

   def parseRequest(request: Request): Option[User] = mocks.Chap10.parseRequestOpt(request)

   def getAccount(user: User): Option[Account] = mocks.Chap10.getAccountOpt(user)

   def applyOperation(account: Account, op: Operation): Option[Int] =
      mocks.Chap10.applyOperationOpt(account, op)

   def demo3(request: Request, op: Operation): Option[Int] =
      parseRequest(request) match
         case None => None
         case Some(user) =>
            getAccount(user) match
               case None          => None
               case Some(account) => applyOperation(account, op)

   def demo4: Option[Int] = Some(42).map((x: Int) => x + 1) // Some(43)

   def demo5: Option[Int] = None.map((x: Int) => x + 1) // None

   def demo6(request: Request, op: Operation): Option[Option[Option[Int]]] =
      parseRequest(request)
         .map(user => getAccount(user).map(account => applyOperation(account, op)))

   def demo7(request: Request, op: Operation): Option[Int] =
      parseRequest(request)
         .flatMap(user => getAccount(user))
         .flatMap(account => applyOperation(account, op))
