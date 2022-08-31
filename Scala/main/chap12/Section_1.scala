package chap12

import mocks.Chap12.ip

import java.net.InetAddress
import java.util.Properties
import java.util.logging.Logger

object Section_1:
   def demo1(): Unit =
      val logger: Logger = mocks.Chap12.logger

      logger.info(s"incoming request from ${ip.getHostName} (${ip.getHostAddress})")

   def demo2(logger: Logger): Unit =
      logger.info(() => s"incoming request from ${ip.getHostName} (${ip.getHostAddress})")

   val properties: Properties = System.getProperties

   def demo3: String = properties.getProperty("hostname", "unknown")

   extension (properties: Properties)
      def getProperty(key: String, fallback: () => String): String =
         val prop = properties.getProperty(key)
         if prop ne null then prop else fallback()

   def demo4: String =
      properties.getProperty("hostname", () => InetAddress.getLocalHost.getHostName)
