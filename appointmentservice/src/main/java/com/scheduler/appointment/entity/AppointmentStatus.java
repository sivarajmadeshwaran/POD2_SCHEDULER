package com.scheduler.appointment.entity;

/**
 * <h1>Appointment status enum</h1>
 *
 */
public enum AppointmentStatus {

	SCHEDULED(1, "Scheduled"), MODIFIED(2, "Updated"), DELETED(3, "Deleted");

	private int statusId;

	private String status;

	AppointmentStatus(int statusId, String status) {
		this.statusId = statusId;
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
