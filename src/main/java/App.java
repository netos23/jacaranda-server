import com.sun.deploy.net.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

public class App {
	public static void main(String[] args) {
		try {
			ServerSocket socket = new ServerSocket(8080);
			Socket accept = socket.accept();

			BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
			InputReader in = new InputReader(reader);
			while (true){
				System.out.println(in.nextToken());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(BufferedReader reader) {
			this.reader = reader;
			tokenizer = null;
		}

		public String nextToken() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}

	}
}
