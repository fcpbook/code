package chap27

import mocks.Chap25.*
import reactor.core.publisher.{ Flux, Mono }

import java.time.Duration
import java.util.concurrent.{ CompletableFuture, Executor }

object Section_6:
   def makePages(requests: Flux[Request])(using exec: Executor): Flux[Page] =
      requests.flatMap { request =>
         val adF   = CompletableFuture.supplyAsync(() => fetchAd(request), exec)
         val dataF = CompletableFuture.supplyAsync(() => dbLookup(request), exec)
         val pageF = dataF.thenCombine(adF, makePage)
         Mono.fromFuture(pageF)
      }

   class Demo1:
      val pages: Flux[Page] = mocks.Chap27.pages

      val largestPastHour: Flux[Int] =
         pages
            .map(_.length)
            .window(Duration.ofHours(1), Duration.ofMinutes(5))
            .flatMap(lengths => lengths.reduce(0, Integer.max))
   end Demo1

   class Demo1Test: // same as Demo1, but faster
      val pages: Flux[Page] = mocks.Chap27.pages

      val largestPastHour: Flux[Int] =
         pages
            .map(_.length)
            .window(Duration.ofSeconds(9), Duration.ofSeconds(6))
            .flatMap(lengths => lengths.reduce(0, Integer.max))
   end Demo1Test
