package ru.fbtw.jacarndaserver.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private ServerSocket mainSocket;


	public HttpServer(int socket) throws IOException {
		mainSocket = new ServerSocket(socket);
	}

	public HttpServer() throws IOException {
		this(0);
	}


	public void listen() throws IOException {
		while (true){
			Socket clientSocket = mainSocket.accept();

		}
	}

	public ServerSocket getMainSocket() {
		return mainSocket;
	}

}
