package idv.hsiehpinghan.webappdemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import idv.hsiehpinghan.webappdemo.criteria.FacebookFeedCriteria;
import idv.hsiehpinghan.webappdemo.service.DataService;

@Controller
@RequestMapping(value = "/demo")
public class DemoController {
	@Autowired
	private DataService dataService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "demo/index";
	}

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public String facebook() {
		return "demo/facebook";
	}
	
	/**
	 * http://localhost:8081/webapp-demo/demo/facebook/feed?pageId=6815841748
	 * 
	 * @param criteria
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/facebook/feed", method = RequestMethod.GET, produces = { "application/json" })
	public String facebookFeed(FacebookFeedCriteria criteria) throws IOException {
		String pageId = criteria.getPageId();
		String str = dataService.getFacebookFeeds(pageId);
		return str;
	}
}
