package idv.hsiehpinghan.jpademo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntity {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_dt", nullable = false)
	private Date createdDt;
	@Column(name = "created_by")
	private String createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_dt")
	private Date updatedDt;
	@Column(name = "updated_by")
	private String updatedBy;

	public BaseEntity() {
	}

	public BaseEntity(Date createdDt, String createdBy, Date updatedDt, String updatedBy) {
		super();
		this.createdDt = createdDt;
		this.createdBy = createdBy;
		this.updatedDt = updatedDt;
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
