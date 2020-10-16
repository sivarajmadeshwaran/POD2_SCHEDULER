package com.scheduler.appointment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dc_slot")
@NamedQuery(name = "AppointmentSlot.findAll", query = "SELECT d FROM AppointmentSlot d")
public class AppointmentSlot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp")
	private Date createdTimestamp;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	@Column(name="booking_slot")
	private String bookingSlot;
	
	@Column(name="dc_nbr")
	private int dcNumber;
	
	
//	@EmbeddedId
//	private DcSlotPK dcSlotPK;

	public String getBookingSlot() {
		return bookingSlot;
	}

	public void setBookingSlot(String bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

	public int getDcNumber() {
		return dcNumber;
	}

	public void setDcNumber(int dcNumber) {
		this.dcNumber = dcNumber;
	}

	@Column(name="max_truck_count")
	private int maxTruckCount;

	public int getMaxTruckCount() {
		return maxTruckCount;
	}

	public void setMaxTruckCount(int maxTruckCount) {
		this.maxTruckCount = maxTruckCount;
	}
//
//	public DcSlotPK getDcSlotPK() {
//		return dcSlotPK;
//	}
//
//	public void setDcSlotPK(DcSlotPK dcSlotPK) {
//		this.dcSlotPK = dcSlotPK;
//	}

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

}
