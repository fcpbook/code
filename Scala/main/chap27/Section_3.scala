package chap27

import mocks.Chap25.*

import java.util.concurrent.ForkJoinTask

object Section_3:
   def demo(request: Request): Page =
      val futureAd: ForkJoinTask[Ad]     = ForkJoinTask.adapt(() => fetchAd(request)).fork()
      val futureData: ForkJoinTask[Data] = ForkJoinTask.adapt(() => dbLookup(request)).fork()
      val data: Data                     = futureData.join()
      val ad: Ad                         = futureAd.join()
      val page: Page                     = makePage(data, ad)

      page
