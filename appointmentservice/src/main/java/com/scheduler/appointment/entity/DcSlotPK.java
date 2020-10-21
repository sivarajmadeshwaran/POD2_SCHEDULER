package com.scheduler.appointment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DcSlotPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "dc_nbr")
	private int dcNbr;

	@Column(name = "booking_slot")
	private String bookingSlot;

	public int getDcNbr() {
		return dcNbr;
	}

	public void setDcNbr(int dcNbr) {
		this.dcNbr = dcNbr;
	}

	public String getBookingSlot() {
		return bookingSlot;
	}

	public void setBookingSlot(String bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

}
