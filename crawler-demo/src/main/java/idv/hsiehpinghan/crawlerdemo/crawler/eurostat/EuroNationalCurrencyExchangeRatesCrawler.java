package idv.hsiehpinghan.crawlerdemo.crawler.eurostat;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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

import idv.hsiehpinghan.crawlerdemo.service.EuroNationalCurrencyExchangeRatesService;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity;
import idv.hsiehpinghan.jpademo.entity.eurostat.EuroNationalCurrencyExchangeRatesEntity.EuroNationalCurrencyExchangeRatesId;

/**
 * crawler for EUROSTAT currency excahnge rates :
 * http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=
 * teimf200&plugin=1
 * 
 * @author hsiehpinghan
 *
 */
@Component
public class EuroNationalCurrencyExchangeRatesCrawler {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final NumberFormat FORMATTER = NumberFormat.getNumberInstance();
	private final String URL = "http://ec.europa.eu/eurostat/tgm/table.do?tab=table&init=1&language=en&pcode=teimf200&plugin=1";
	private final String CLASS_NAME = this.getClass().getSimpleName();
	@Autowired
	private EuroNationalCurrencyExchangeRatesService service;

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
		List<String> yearMonths = getYearMonths(doc);
		List<String> currencies = getCurrencies(doc);
		List<List<String>> data = getData(doc);
		Date now = new Date();
		int entityAmt = 0;
		Collection<EuroNationalCurrencyExchangeRatesEntity> entities = new ArrayList<EuroNationalCurrencyExchangeRatesEntity>(
				yearMonths.size() * currencies.size());
		for (int r = 0, rSize = data.size(); r < rSize; ++r) {
			List<String> row = data.get(r);
			for (int c = 0, cSize = row.size(); c < cSize; ++c) {
				String yearMonth = yearMonths.get(c);
				String currency = currencies.get(r);
				BigDecimal value = null;
				try {
					value = getValue(row.get(c));
				} catch (ParseException e) {
					LOGGER.error("parsing value(" + row.get(c) + ") fail !!!", e);
					continue;
				}
				EuroNationalCurrencyExchangeRatesId id = new EuroNationalCurrencyExchangeRatesId(yearMonth, currency);
				EuroNationalCurrencyExchangeRatesEntity entity = new EuroNationalCurrencyExchangeRatesEntity(now,
						CLASS_NAME, now, CLASS_NAME, id, value);
				entities.add(entity);
				LOGGER.info("entity(" + entity + ") add to entities.");
				++entityAmt;
			}
		}
		service.saveOrUpdate(entities);
		LOGGER.info("saveOrUpdate success, entity amount : " + entityAmt);
	}

	private BigDecimal getValue(String value) throws ParseException {
		return new BigDecimal(FORMATTER.parse(value).toString());
	}

	private List<String> getYearMonths(Document doc) {
		Element headTable = doc.getElementById("headtable");
		Elements yearMonthThs = headTable.getElementsByTag("th");
		List<String> yearMonths = new ArrayList<String>(yearMonthThs.size());
		for (Element yearMonthTh : yearMonthThs) {
			yearMonths.add(yearMonthTh.text());
		}
		return yearMonths;
	}

	private List<String> getCurrencies(Document doc) {
		Element headTable = doc.getElementById("fixtable");
		Elements currencyThs = headTable.getElementsByTag("th");
		List<String> currencies = new ArrayList<String>(currencyThs.size());
		for (Element currencyTh : currencyThs) {
			currencies.add(currencyTh.text());
		}
		return currencies;
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
