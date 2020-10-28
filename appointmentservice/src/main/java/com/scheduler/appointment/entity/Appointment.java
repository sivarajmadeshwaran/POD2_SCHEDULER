package com.scheduler.appointment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;


/**
 * <h1>Appointment table entity</h1>
 *
 */
@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="appointment_id")
	private Integer appiointmentId;

	@Column(name = "dc_nbr")
	private String dcNumber;

	@Column(name="booking_slot_id")
	private Integer appointmentSlotId;
	
	@Column(name="appt_status_id")
	private int appointmentStatusId;

	public Integer getAppointmentSlotId() {
		return appointmentSlotId;
	}

	public int getAppointmentStatusId() {
		return appointmentStatusId;
	}

	public void setAppointmentStatusId(int appointmentStatusId) {
		this.appointmentStatusId = appointmentStatusId;
	}

	public void setAppointmentSlotId(Integer appointmentSlotId) {
		this.appointmentSlotId = appointmentSlotId;
	}

	@Column(name = "appt_date")
	private Date appointmentDate;

	@Column(name = "truck_nbr")
	private int truckNumber;

	@Column(name = "created_timestamp")
	private Date createdTimeStamp;

	@Column(name = "last_updated_timestamp")
	@UpdateTimestamp
	private Date lastUpdateTimeStamp;

	@Column(name = "qty")
	private int qty;
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Integer getAppiointmentId() {
		return appiointmentId;
	}

	public void setAppiointmentId(Integer appiointmentId) {
		this.appiointmentId = appiointmentId;
	}

	public String getDcNumber() {
		return dcNumber;
	}

	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public int getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(int truckNumber) {
		this.truckNumber = truckNumber;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Date getLastUpdateTimeStamp() {
		return lastUpdateTimeStamp;
	}

	public void setLastUpdateTimeStamp(Date lastUpdateTimeStamp) {
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
	}

	
}
