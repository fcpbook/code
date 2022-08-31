package chap27

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import mocks.*

@Suppress("UNUSED_VARIABLE")
suspend fun demo(request: Request) =
  coroutineScope {
    val futureAd: Deferred<Ad> = async { fetchAd(request) }
    val futureData: Deferred<Data> = async { dbLookup(request) }
    val data: Data = futureData.await()
    val ad: Ad = futureAd.await()
    val page: Page = makePage(data, ad)
  }
