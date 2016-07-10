package idv.hsiehpinghan.jerseydemo.document;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class NewsDocument {
	@Field
	private String id;
	@Field
	private List<String> title;
	@Field
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
