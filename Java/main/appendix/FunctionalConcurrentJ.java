package appendix;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static mocks.Chap25.*;

@SuppressWarnings("Convert2MethodRef")
class FunctionalConcurrentJ {
  private static final int port = mocks.Chap21.port();

  static class Server {
    ExecutorService exec = Executors.newFixedThreadPool(16);

    ServerSocket server;
    Server() throws IOException {
      server = new ServerSocket(port);
    }

    void handleConnection(Connection connection) {
      var requestF = CompletableFuture.supplyAsync(connection::read, exec);
      var adF = requestF.thenApplyAsync(request -> fetchAd(request), exec);
      var dataF = requestF.thenApplyAsync(request -> dbLookup(request), exec);
      var pageF = dataF.thenCompose(data ->
          adF.thenApplyAsync(ad -> makePage(data, ad), exec)
      );
      dataF.thenAcceptAsync(data -> addToLog(data), exec);
      pageF.thenAcceptAsync(page -> updateStats(page), exec);
      pageF.thenAcceptAsync(page -> {
        connection.write(page);
        connection.close();
      }, exec);
    }

    void demo() throws IOException {
      while (true) handleConnection(new Connection(server.accept()));
    }
  }
}
