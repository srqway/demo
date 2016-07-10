package idv.hsiehpinghan.webappdemo.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import idv.hsiehpinghan.webappdemo.utility.UrlUtility;

@Service
public class DataService {
	public String getFacebookFeeds(String pageId) throws IOException {
		final String url = "http://localhost:8080/jersey-demo/facebook/" + pageId + "/feed";
		return UrlUtility.getContent(url);
	}
}
