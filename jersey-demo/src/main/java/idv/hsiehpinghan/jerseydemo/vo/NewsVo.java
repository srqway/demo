package idv.hsiehpinghan.jerseydemo.vo;

public class NewsVo {

	private String title;
	private String url;

	public NewsVo(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "NewsVo [title=" + title + ", url=" + url + "]";
	}

}
