package mocks

import mocks.Chap25.Page
import reactor.core.publisher.Flux

import java.time.Duration

object Chap27:
   val pages: Flux[Page] = Flux
      .just(1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1)
      .delayElements(Duration.ofSeconds(2))
      .map(i => Page(i))
