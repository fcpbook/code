package chap27

import cps.compat.sip22.{ async, await }
import mocks.Chap25.*

import scala.concurrent.{ ExecutionContext, Future }

//noinspection DuplicatedCode
object Section_4:
   def demo(request: Request)(using ExecutionContext): Future[Page] = async {
      val futureAd: Future[Ad]     = Future(fetchAd(request))
      val futureData: Future[Data] = Future(dbLookup(request))
      val ad: Ad                   = await(futureAd)
      val data: Data               = await(futureData)
      val page: Page               = makePage(data, ad)

      page
   }
