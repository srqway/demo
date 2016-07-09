package idv.hsiehpinghan.jerseydemo.vo;

import java.math.BigDecimal;

public class GdpVo {
	private String quarter;
	private String area;
	private BigDecimal value;

	public GdpVo(String quarter, String area, BigDecimal value) {
		super();
		this.quarter = quarter;
		this.area = area;
		this.value = value;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
