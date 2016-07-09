package idv.hsiehpinghan.crawlerdemo.crawler.eurostat;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import idv.hsiehpinghan.crawlerdemo.service.GrossDomesticProductCurrentPricesService;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.GrossDomesticProductCurrentPricesEntity.GrossDomesticProductCurrentPricesId;

/**
 * crawler for EUROSTAT GDP :
 * http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=
 * teina010&plugin=1
 * 
 * @author hsiehpinghan
 *
 */
@Component
public class GrossDomesticProductCurrentPricesCrawler {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final NumberFormat FORMATTER = NumberFormat.getNumberInstance();
	private final String URL = "http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=teina010&plugin=1";
	private final String CLASS_NAME = this.getClass().getSimpleName();
	@Autowired
	private GrossDomesticProductCurrentPricesService service;

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
		Date now = new Date();
		int entityAmt = 0;
		for (int r = 0, rSize = data.size(); r < rSize; ++r) {
			List<String> row = data.get(r);
			for (int c = 0, cSize = row.size(); c < cSize; ++c) {
				String quarter = quarters.get(c).substring(0, 6);
				String area = areas.get(r);
				BigDecimal value = null;
				try {
					value = getValue(row.get(c));
				} catch (ParseException e) {
					LOGGER.error("parsing value(" + row.get(c) + ") fail !!!", e);
					continue;
				}
				GrossDomesticProductCurrentPricesId id = new GrossDomesticProductCurrentPricesId(quarter, area);
				GrossDomesticProductCurrentPricesEntity entity = new GrossDomesticProductCurrentPricesEntity(now,
						CLASS_NAME, now, CLASS_NAME, id, value);
				service.saveOrUpdate(entity);
				LOGGER.info("entity(" + entity + ") saved or updated.");
				++entityAmt;
			}
		}
		LOGGER.info("crawl success, entity amount : " + entityAmt);
	}

	private BigDecimal getValue(String value) throws ParseException {
		String v = null;
		if (value.endsWith("p")) {
			v = value.substring(0, value.length() - 1);
		} else {
			v = value;
		}
		return new BigDecimal(FORMATTER.parse(v).toString());
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
			for (Element quarterTd : quarterTds) {
				quarterData.add(quarterTd.text());
			}
			areaData.add(quarterData);
		}
		return areaData;
	}
}
