import ru.fbtw.jacarndaserver.server.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class App {
	public static void main(String[] args) throws IOException {
		new HttpServer(8080,100).listen();
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
