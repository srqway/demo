package idv.hsiehpinghan.jerseydemo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class FacebookService implements InitializingBean {
	private String appId;
	private String appSecret;
	private String siteUrl;
	@Autowired
	private Environment environment;

	@Override
	public void afterPropertiesSet() throws Exception {
		appId = environment.getRequiredProperty("appId");
		appSecret = environment.getRequiredProperty("appSecret");
		siteUrl = environment.getRequiredProperty("siteUrl");
	}

	public String getFeeds(String pageId) throws IOException {
		String appAccessToken = getAppAccessToken().split("=")[1];
		final String httpsUrl = "https://graph.facebook.com/" + pageId + "/feed?fields=message&access_token="
				+ appAccessToken;
		return getContent(httpsUrl);
	}

	private String getAppAccessToken() throws IOException {
		String httpsUrl = "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&client_secret="
				+ appSecret + "&grant_type=client_credentials" + "&redirect_uri=" + siteUrl;
		return getContent(httpsUrl);
	}

	private String getContent(String httpsUrl) throws IOException {
		HttpsURLConnection connection = getHttpsURLConnection(httpsUrl);
		return getContent(connection);
	}

	private HttpsURLConnection getHttpsURLConnection(String httpsUrl) throws IOException {
		URL url = new URL(httpsUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		int responseCode = connection.getResponseCode();
		if (responseCode != 200) {
			throw new RuntimeException("responseCode(" + responseCode + ") not equals to 200 !!!");
		}
		return connection;
	}

	private String getContent(HttpsURLConnection connection) throws IOException {
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
