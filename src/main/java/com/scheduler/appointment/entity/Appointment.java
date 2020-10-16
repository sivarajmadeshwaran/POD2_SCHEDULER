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


@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer appiointmentId;

	@Column(name = "dc_nbr")
	private String dcNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	private AppointmentSlot AppointmentSlot;

	@Column(name = "appt_date")
	private Date appointmentDate;

	@Column(name = "truck_nbr")
	private int truckNumber;

	@Column(name = "created_timestamp")
	private String createdTimeStamp;

	@Column(name = "last_updated_timestamp")
	private String lastUpdateTimeStamp;

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

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getLastUpdateTimeStamp() {
		return lastUpdateTimeStamp;
	}

	public void setLastUpdateTimeStamp(String lastUpdateTimeStamp) {
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
	}

	public AppointmentSlot getAppointmentSlot() {
		return AppointmentSlot;
	}

	public void setAppointmentSlot(AppointmentSlot appointmentSlot) {
		AppointmentSlot = appointmentSlot;
	}

}
