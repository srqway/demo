package idv.hsiehpinghan.webappdemo.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtility {
	public static String getContent(String httpUrl) throws IOException {
		HttpURLConnection connection = getHttpURLConnection(httpUrl);
		return getContent(connection);
	}

	private static HttpURLConnection getHttpURLConnection(String httpUrl) throws IOException {
		URL url = new URL(httpUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		int responseCode = connection.getResponseCode();
		if (responseCode != 200) {
			throw new RuntimeException("responseCode(" + responseCode + ") not equals to 200 !!!");
		}
		return connection;
	}

	private static String getContent(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		return sb.toString();
	}
}
