package idv.hsiehpinghan.jerseydemo.vo;

import java.math.BigDecimal;
import java.util.List;

public class EconomyVo {
	private List<String> legends; // Euro area (changing composition)
	private List<String> xAxises; // 2013Q2
	private List<List<BigDecimal>> data; //

	public EconomyVo(List<String> legends, List<String> xAxises, List<List<BigDecimal>> data) {
		super();
		this.legends = legends;
		this.xAxises = xAxises;
		this.data = data;
	}

	public List<String> getLegends() {
		return legends;
	}

	public void setLegends(List<String> legends) {
		this.legends = legends;
	}

	public List<String> getxAxises() {
		return xAxises;
	}

	public void setxAxises(List<String> xAxises) {
		this.xAxises = xAxises;
	}

	public List<List<BigDecimal>> getData() {
		return data;
	}

	public void setData(List<List<BigDecimal>> data) {
		this.data = data;
	}

}
