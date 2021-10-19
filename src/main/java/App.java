import ru.fbtw.jacarandaserver.core.server.HttpServer;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		ServerConfiguration context = new ServerConfiguration("http", "127.0.0.1:8080", "./assets",
				8080, 100, "HTTP/1.1",
				"assets/error.html",
				"assets/dirinfo.html",
				"jacaranda server/0.1.0 (Unix) (Mac os)");
		new HttpServer(context).start();
	}
}
