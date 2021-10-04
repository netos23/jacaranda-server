import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.fbtw.jacarandaserver.server.HttpServer;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpHandler httpHandler = new HttpHandler() {

            @Override
            public void handle(HttpExchange exchange) throws IOException {

            }
        };
        ServerContext context = new ServerContext("http", "127.0.0.1:8080", "./assets",
                8080, 100, "HTTP/1.1",
                "assets/error.html",
                "assets/dirinfo.html",
                "jacaranda server/0.1.0 (Unix) (Mac os)");
        new HttpServer(context).listen();
    }
}
