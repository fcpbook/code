package chap21

import mocks.Chap21.{ handleConnection, port }

import java.net.ServerSocket
import java.util.concurrent.Executors

//noinspection TypeAnnotation
object Section_2:
   class SeqServer:
      val server = ServerSocket(port)

      def demo(): Unit =
         while true do
            val socket = server.accept()
            handleConnection(socket)
   end SeqServer

   class ParServerPool:
      val server = ServerSocket(port)
      val exec   = Executors.newFixedThreadPool(16)

      def demo(): Unit =
         while true do
            val socket = server.accept()
            exec.execute(() => handleConnection(socket))
   end ParServerPool

   class ParServerThread:
      val server = ServerSocket(port)

      def demo(): Unit =
         // DON'T DO THIS!
         while true do
            val socket = server.accept()
            Thread(() => handleConnection(socket)).start()
   end ParServerThread
