package com.scheduler.appointment.Dto;

import java.util.Date;

public class AppointmentDto {

	
	private String dcNumber;
	
	private int bookingSlot;
	
	private String createdTimeStamp;
	
	private String lastUpdateTimeStamp;
	
	
	private AppointmentSlotDto appointmentSlotDto;
	public AppointmentSlotDto getAppointmentSlotDto() {
		return appointmentSlotDto;
	}

	public void setAppointmentSlotDto(AppointmentSlotDto appointmentSlotDto) {
		this.appointmentSlotDto = appointmentSlotDto;
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

	public int getBookingSlot() {
		return bookingSlot;
	}

	public void setBookingSlot(int bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

	private Date appointmentDate;
	
	private String truckNumber;
	
	public String getDcNumber() {
		return dcNumber;
	}

	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}

//	public String getBookingSlot() {
//		return bookingSlot;
//	}
//
//	public void setBookingSlot(String bookingSlot) {
//		this.bookingSlot = bookingSlot;
//	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}
}
