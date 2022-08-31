package chap27

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ ActorRef, Behavior }
import mocks.Chap25.*

//noinspection ScalaUnusedSymbol
object Section_5:
   case class RequestMsg(replyTo: ActorRef[PageMsg], request: Request)
   case class AdMsg(ad: Ad)
   case class PageMsg(page: Page)

   def requestHandling(): Behavior[RequestMsg] =
      Behaviors.receivePartial {
         case (context, RequestMsg(replyTo, request)) =>
            val dbQueryingActor = context.spawnAnonymous(dbQuerying(replyTo, request))
            context.spawnAnonymous(adFetching(dbQueryingActor, request))
            Behaviors.same
      }

   def adFetching(replyTo: ActorRef[AdMsg], request: Request): Behavior[Nothing] =
      Behaviors.setup { context =>
         val ad = fetchAd(request)
         replyTo ! AdMsg(ad)
         Behaviors.stopped
      }

   def dbQuerying(replyTo: ActorRef[PageMsg], request: Request): Behavior[AdMsg] =
      Behaviors.setup { context =>
         val data = dbLookup(request)

         Behaviors.receiveMessagePartial {
            case AdMsg(ad) =>
               val page = makePage(data, ad)
               replyTo ! PageMsg(page)
               Behaviors.stopped
         }
      }
   end dbQuerying

   trait Command
   case class Start(replyTo: ActorRef[Number]) extends Command
   case class Number(value: Int)               extends Command
   case object Stop                            extends Command

   def reset(): Behavior[Command] =
      Behaviors.receiveMessagePartial {
         case Start(replyTo) => add(replyTo, Behaviors.same)
      }

   def add(replyTo: ActorRef[Number], reset: Behavior[Command]): Behavior[Command] =
      var sum = 0
      Behaviors.receiveMessagePartial {
         case Number(value) =>
            sum += value
            Behaviors.same
         case Stop =>
            replyTo ! Number(sum)
            reset
      }
