package chap21

import tinyscalautils.text.{ println, threadTimeDemoMode }

import java.net.URL
import scala.collection.parallel.CollectionConverters.seqIsParallelizable
import scala.collection.parallel.ParSeq
import scala.io.Codec.UTF8
import scala.io.Source
import scala.util.Using

//noinspection ScalaUnusedSymbol,TypeAnnotation
object Section_4:
   object Demo1:
      println("START")

      def distinctWordsCount(url: URL): Int =
         println(s"start $url")
         val count =
            Using.resource(Source.fromURL(url)(UTF8)) { source =>
               source
                  .getLines()
                  .flatMap(line => line.split("""\b"""))
                  .map(word => word.filter(_.isLetter).toLowerCase)
                  .filter(_.nonEmpty)
                  .distinct
                  .size
            }
         println(s"end $url")
         count

      val urls: List[URL] = mocks.Chap21.urls

      val counts = urls.map(distinctWordsCount)
      println(counts.max)

      println("END")
   end Demo1

   import Demo1.{ distinctWordsCount, urls }

   object Demo2:
      import scala.collection.parallel.CollectionConverters.ImmutableSeqIsParallelizable

      val counts = urls.par.map(distinctWordsCount)
      println(counts.max)
   end Demo2

   object Demo3:
      import Demo2.counts

      // DON'T DO THIS!
      var max = Int.MinValue
      for count <- counts do if count > max then max = count
   end Demo3

   object Demo4:
      import Demo2.counts

      var max = Int.MinValue
      for count <- counts.seq do if count > max then max = count
   end Demo4

   object Demo5:
      val max = urls.foldLeft(Int.MinValue)(_ max distinctWordsCount(_))

   object Demo6:
      // DON'T DO THIS!
      val max = urls.par.foldLeft(Int.MinValue)(_ max distinctWordsCount(_))

   object Demo7:
      val max = urls.par.aggregate(Int.MinValue)(_ max distinctWordsCount(_), _ max _)

   object Demo8:
      def distinctWordsCount(url: URL): Int =
         Using.resource(Source.fromURL(url)(UTF8)) { source =>
            source
               .getLines()
               .to(ParSeq)
               .flatMap(line => line.split("""\b"""))
               .map(word => word.filter(_.isLetter).toLowerCase)
               .filter(_.nonEmpty)
               .distinct
               .size
         }

   def demo9: List[Int] =
      List(1, 2, 3).par.map(_ * 10).toList // List(10, 20, 30), in this order

   @main def TestDemos(): Unit = // uncomment to run a demo
      println("START")

      // println(Demo1.counts.max)
      // println(Demo2.counts.max)
      // println(Demo4.max)
      // println(Demo5.max)
      // println(Demo6.max)
      // println(Demo7.max)
      // println(Demo8.distinctWordsCount(mocks.Chap21.urls.head))

      println("END")
   end TestDemos
