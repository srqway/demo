package idv.hsiehpinghan.jerseydemo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idv.hsiehpinghan.jerseydemo.document.NewsDocument;
import idv.hsiehpinghan.jerseydemo.vo.NewsVo;

@Service
public class NewsService {
	private final Integer ROWS = 30;
	@Autowired
	private HttpSolrServer httpSolrServer;

	public List<NewsVo> query(String term, Integer start) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		if (term == null) {
			query.setQuery("*:*");
		} else {
			query.setQuery("content:" + term);
		}
		query.setFields("id", "title", "url");
		query.setStart(start);
		query.setRows(ROWS);
		List<NewsDocument> docs = httpSolrServer.query(query).getBeans(NewsDocument.class);
		List<NewsVo> vos = new ArrayList<>(docs.size());
		for (NewsDocument doc : docs) {
			vos.add(convertToNewsVo(doc));
		}
		return vos;
	}

	private NewsVo convertToNewsVo(NewsDocument doc) {
		List<String> titles = doc.getTitle();
		String id = doc.getId();
		String source;
		if (id.startsWith("com.cnn")) {
			source = "CNN";
		} else if (id.startsWith("com.bbc")) {
			source = "BBC";
		} else {
			source = "";
		}
		String title = titles.size() > 0 ? doc.getTitle().get(0) : null;
		String url = doc.getUrl();
		return new NewsVo(source, title, url);
	}
}
