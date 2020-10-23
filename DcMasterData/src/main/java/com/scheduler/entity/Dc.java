package com.scheduler.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name = "dc")
public class Dc implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="dc_nbr")
	private int dc_number;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "dc_type", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private DcType dcTypeBean;
	
	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@NotNull
	private Date createdAt;
	
	@Column(name="city")
	private String dc_city;
	
	@Column(name = "last_updated_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	@NotNull
	private Date updatedAt;

	public Dc() {
	}

	public int getDc_number() {
		return dc_number;
	}

	public void setDc_number(int dc_number) {
		this.dc_number = dc_number;
	}

	public DcType getDcTypeBean() {
		return dcTypeBean;
	}

	public void setDcTypeBean(DcType dcTypeBean) {
		this.dcTypeBean = dcTypeBean;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDc_city() {
		return dc_city;
	}

	public void setDc_city(String dc_city) {
		this.dc_city = dc_city;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}