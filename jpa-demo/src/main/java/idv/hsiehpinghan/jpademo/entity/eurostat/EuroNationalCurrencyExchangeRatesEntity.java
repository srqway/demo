package idv.hsiehpinghan.jpademo.entity.eurostat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "EURO_NATIONAL_CURRENCY_EXCHANGE_RATES_ENTITY", indexes = { @Index(columnList = "year_month"),
		@Index(columnList = "currency") })
public class EuroNationalCurrencyExchangeRatesEntity extends BaseEntity {
	@EmbeddedId
	private EuroNationalCurrencyExchangeRatesId id;
	@Column(nullable = false)
	private BigDecimal value;

	public EuroNationalCurrencyExchangeRatesEntity() {
		super();
	}

	public EuroNationalCurrencyExchangeRatesEntity(Date createdDt, String createdBy, Date updatedDt, String updatedBy,
			EuroNationalCurrencyExchangeRatesId id, BigDecimal value) {
		super(createdDt, createdBy, updatedDt, updatedBy);
		this.id = id;
		this.value = value;
	}

	public EuroNationalCurrencyExchangeRatesId getId() {
		return id;
	}

	public void setId(EuroNationalCurrencyExchangeRatesId id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EuroNationalCurrencyExchangeRatesEntity other = (EuroNationalCurrencyExchangeRatesEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrossDomesticProductCurrentPricesEntity [id=" + id + ", value=" + value + "]";
	}

	@Embeddable
	public static class EuroNationalCurrencyExchangeRatesId implements Serializable {
		private static final long serialVersionUID = 1L;
		private String currency;
		@Column(name = "year_month")
		private String yearMonth;

		public EuroNationalCurrencyExchangeRatesId() {
			super();
		}

		public EuroNationalCurrencyExchangeRatesId(String yearMonth, String currency) {
			super();
			this.yearMonth = yearMonth;
			this.currency = currency;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((currency == null) ? 0 : currency.hashCode());
			result = prime * result + ((yearMonth == null) ? 0 : yearMonth.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EuroNationalCurrencyExchangeRatesId other = (EuroNationalCurrencyExchangeRatesId) obj;
			if (currency == null) {
				if (other.currency != null)
					return false;
			} else if (!currency.equals(other.currency))
				return false;
			if (yearMonth == null) {
				if (other.yearMonth != null)
					return false;
			} else if (!yearMonth.equals(other.yearMonth))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "EuroNationalCurrencyExchangeRatesId [yearMonth=" + yearMonth + ", currency=" + currency + "]";
		}

	}
}
