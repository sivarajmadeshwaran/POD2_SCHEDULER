package com.scheduler.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="dc_slot")
@Getter
@Setter
@NoArgsConstructor
public class DcSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DcSlotPK slotId;

	@Column(name="id")
	private int id;
	
	@Column(name="max_trucks")
	private int maxTrucks;
	
	@Column(name="obsolete_indicator")
	@NotNull
	private String obsolete_indicator;
	
	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@NotNull
	private Date createdTimestamp;
	
	@Column(name = "last_updated_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	@NotNull
	private Date lastUpdatedTimestamp;

	public DcSlot() {}

	public DcSlotPK getSlotId() {
		return slotId;
	}

	public void setSlotId(DcSlotPK slotId) {
		this.slotId = slotId;
	}
	
	public int getMaxTrucks() {
		return maxTrucks;
	}

	public void setMaxTrucks(int maxTrucks) {
		this.maxTrucks = maxTrucks;
	}
	
	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	
	public String getObsolete_indicator() {
		return obsolete_indicator;
	}

	public void setObsolete_indicator(String obsolete_indicator) {
		this.obsolete_indicator = obsolete_indicator;
	}

	
}
