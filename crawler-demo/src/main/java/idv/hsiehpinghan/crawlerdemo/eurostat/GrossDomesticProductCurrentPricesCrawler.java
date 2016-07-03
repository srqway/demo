package idv.hsiehpinghan.crawlerdemo.eurostat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * from :
 * http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=
 * teina010&plugin=1
 * 
 * @author hsiehpinghan
 *
 */
@Component
public class GrossDomesticProductCurrentPricesCrawler {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final String URL = "http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=teina010&plugin=1";

	/**
	 * crawling time interval : 1 day.
	 * 
	 * @throws IOException
	 */
	@Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
	public void crawl() {
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).timeout(10000).get();
		} catch (IOException e) {
			LOGGER.error("connect to URL(" + URL + ") fail !!!", e);
			return;
		}
		LOGGER.info("connected to URL(" + URL + ") success.");
		List<String> quarters = getQuarters(doc);
		List<String> areas = getAreas(doc);
		List<List<String>> data = getData(doc);
		System.err.println(data);
		
	}

	private List<String> getQuarters(Document doc) {
		Element headTable = doc.getElementById("headtable");
		Elements quarterThs = headTable.getElementsByTag("th");
		List<String> quarters = new ArrayList<String>(quarterThs.size());
		for (Element quarterTh : quarterThs) {
			quarters.add(quarterTh.text());
		}
		return quarters;
	}
	
	private List<String> getAreas(Document doc) {
		Element headTable = doc.getElementById("fixtable");
		Elements quarterThs = headTable.getElementsByTag("th");
		List<String> quarters = new ArrayList<String>(quarterThs.size());
		for (Element quarterTh : quarterThs) {
			quarters.add(quarterTh.text());
		}
		return quarters;
	}
	
	private List<List<String>> getData(Document doc) {
		Element headTable = doc.getElementById("contenttable");
		Elements areaTrs = headTable.getElementsByTag("tr");
		List<List<String>> areaData = new ArrayList<List<String>>(areaTrs.size());
		for (Element areaTr : areaTrs) {
			Elements quarterTds = areaTr.getElementsByTag("td");
			List<String> quarterData = new ArrayList<String>(quarterTds.size());
			for(Element quarterTd : quarterTds) {
				quarterData.add(quarterTd.text());
			}
			areaData.add(quarterData);
		}
		return areaData;
	}
}
