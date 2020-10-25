package com.scheduler.appointment.Dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AppointmentDto {

	
	private String dcNumber;
	
	private int bookingSlot;
	
	private String createdTimeStamp;
	
	private String lastUpdateTimeStamp;
	
	private List<PurchaseOrderDto> pos;	
	
	private int appointmentStatusId;
	
	
	
	public List<PurchaseOrderDto> getPos() {
		return pos;
	}

	public void setPos(List<PurchaseOrderDto> pos) {
		this.pos = pos;
	}

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
	
	private int truckNumber;
	
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

	public void setAppointmentStatusId(int appointmentStatusId) {
		this.appointmentStatusId = appointmentStatusId;
	}
	
	public int getAppointmentStatusId() {
		return appointmentStatusId;
	}

	public void setTruckNumber(int truckNumber) {
		this.truckNumber = truckNumber;
	}
}
