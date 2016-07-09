package idv.hsiehpinghan.jerseydemo.vo;

import java.math.BigDecimal;

public class ExchangeRateVo {
	private String yearMonth;
	private String currency;
	private BigDecimal value;

	public ExchangeRateVo(String yearMonth, String currency, BigDecimal value) {
		super();
		this.yearMonth = yearMonth;
		this.currency = currency;
		this.value = value;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
