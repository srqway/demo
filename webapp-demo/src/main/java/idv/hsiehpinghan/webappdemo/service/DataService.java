package idv.hsiehpinghan.webappdemo.service;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import idv.hsiehpinghan.webappdemo.utility.UrlUtility;

@Service
public class DataService implements InitializingBean {
	private String webServiceHost;
	@Autowired
	private Environment environment;

	@Override
	public void afterPropertiesSet() throws Exception {
		webServiceHost = environment.getRequiredProperty("webServiceHost");
	}

	public String getFacebookFeeds(String pageId) throws IOException {
		final String url = webServiceHost + "facebook/" + pageId + "/feed";
		return UrlUtility.getContent(url);
	}

	public String getNewsData(String term, Integer start) throws IOException {
		final String url = webServiceHost + "news?term=" + term + "&start=" + (start == null ? 0 : start);
		return UrlUtility.getContent(url);
	}

	public String getEconomyData(String type) throws IOException {
		final String url = webServiceHost + "economy/" + type;
		return UrlUtility.getContent(url);
	}
}
