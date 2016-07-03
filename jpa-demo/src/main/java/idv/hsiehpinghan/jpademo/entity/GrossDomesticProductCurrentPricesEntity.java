package idv.hsiehpinghan.jpademo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "GROSS_DOMESTIC_PRODUCT_CURRENT_PRICES_ENTITY", indexes = { @Index(columnList = "quarter"),
		@Index(columnList = "area") })
public class GrossDomesticProductCurrentPricesEntity extends BaseEntity {
	@EmbeddedId
	private GrossDomesticProductCurrentPricesId id;
	@Column(nullable = false)
	private BigDecimal value;

	public GrossDomesticProductCurrentPricesEntity() {
	}

	public GrossDomesticProductCurrentPricesEntity(Date createdDt, String createdBy, Date updatedDt, String updatedBy,
			GrossDomesticProductCurrentPricesId id, BigDecimal value) {
		super(createdDt, createdBy, updatedDt, updatedBy);
		this.id = id;
		this.value = value;
	}

	public GrossDomesticProductCurrentPricesId getId() {
		return id;
	}

	public void setId(GrossDomesticProductCurrentPricesId id) {
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
		GrossDomesticProductCurrentPricesEntity other = (GrossDomesticProductCurrentPricesEntity) obj;
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
	public static class GrossDomesticProductCurrentPricesId implements Serializable {
		private static final long serialVersionUID = 1L;
		private String quarter;
		private String area;

		public GrossDomesticProductCurrentPricesId() {
		}

		public GrossDomesticProductCurrentPricesId(String quarter, String area) {
			super();
			this.quarter = quarter;
			this.area = area;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((area == null) ? 0 : area.hashCode());
			result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
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
			GrossDomesticProductCurrentPricesId other = (GrossDomesticProductCurrentPricesId) obj;
			if (area == null) {
				if (other.area != null)
					return false;
			} else if (!area.equals(other.area))
				return false;
			if (quarter == null) {
				if (other.quarter != null)
					return false;
			} else if (!quarter.equals(other.quarter))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "GrossDomesticProductCurrentPricesId [quarter=" + quarter + ", area=" + area + "]";
		}

	}
}
