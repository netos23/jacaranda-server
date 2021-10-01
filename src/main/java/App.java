import ru.fbtw.jacarandaserver.server.HttpServer;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		ServerContext context = new ServerContext("http","127.0.0.1","/",8080,100);
		new HttpServer(context).listen();
	}
}
