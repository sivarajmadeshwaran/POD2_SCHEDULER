package com.scheduler.appointment.Dto;


/**
 * <h1>Appointment slot Data transfer Object</h1>
 *
 */
public class AppointmentSlotDto {

	private int id;

	private int dcNumber;

	private String bookingSlot;

	private int maxTruckCount;

	private String createdTimeStamp;

	private String lastUpdatedTimeStamp;

	public AppointmentSlotDto(int id,int dcNumber,String bookingSlot,int maxTruckCount,String createdTimeStamp,String lastUpdatedTimeStamp) {
		this.id = id;
		this.dcNumber= dcNumber;
				this.bookingSlot =bookingSlot;
				this.maxTruckCount=maxTruckCount;
				this.createdTimeStamp =createdTimeStamp;
				this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}

	public AppointmentSlotDto() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDcNumber() {
		return dcNumber;
	}

	public void setDcNumber(int dcNumber) {
		this.dcNumber = dcNumber;
	}

	public String getBookingSlot() {
		return bookingSlot;
	}

	public void setBookingSlot(String bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

	public int getMaxTruckCount() {
		return maxTruckCount;
	}

	public void setMaxTruckCount(int maxTruckCount) {
		this.maxTruckCount = maxTruckCount;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}

	public void setLastUpdatedTimeStamp(String lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}
}
