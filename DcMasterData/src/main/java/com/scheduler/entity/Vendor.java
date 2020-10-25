package com.scheduler.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

/**
 * The persistent class for the vendor table in database.
*/


@Entity
@Table(name="vendor")
public class Vendor {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone_nbr")
	private int phone;
	
	@Column(name="email")
	private String mail;
	
	@Column(name="address")
	private String address;
	
	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	@CreationTimestamp
	private Date createdDt;
	
	@Column(name = "last_updated_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	@UpdateTimestamp
	private Date lastUpdatedDt;

	public Vendor() {}
	
	public Vendor(String name, int phone, String mail, String address, Date createdDt, Date lastUpdatedDt) {
		super();
		this.name = name;
		this.phone = phone;
		this.mail = mail;
		this.address = address;
		this.createdDt = createdDt;
		this.lastUpdatedDt = lastUpdatedDt;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}
	
	
}
