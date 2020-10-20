package com.scheduler.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "truck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Truck implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="truck_nbr")
	private int truckNbr;
	
	@Column(name="truck_name")
	private String truckName;

	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	@CreationTimestamp
	private Date createdTimestamp;

	@Column(name = "last_updated_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	@UpdateTimestamp
	private Date lastUpdatedTimestamp;

	//bi-directional many-to-one association to TruckType
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name="truck_type")
	private TruckType truckTypeBean;

	public Truck() {}

	public int getTruckNbr() {
		return this.truckNbr;
	}

	public void setTruckNbr(int truckNbr) {
		this.truckNbr = truckNbr;
	}

	public String getTruckName() {
		return truckName;
	}

	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public TruckType getTruckTypeBean() {
		return this.truckTypeBean;
	}

	public void setTruckTypeBean(TruckType truckTypeBean) {
		this.truckTypeBean = truckTypeBean;
	}

}