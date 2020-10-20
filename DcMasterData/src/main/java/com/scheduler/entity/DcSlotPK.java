package com.scheduler.entity;


import java.io.Serializable;

import javax.persistence.*;


@Embeddable
public class DcSlotPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="dc_nbr")
	private int dcNbr;

	@Column(name="booking_slot")
	private String bookingSlot;
	
	
	public DcSlotPK() {}

	public int getDcNbr() {
		return this.dcNbr;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingSlot == null) ? 0 : bookingSlot.hashCode());
		result = prime * result + dcNbr;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DcSlotPK other = (DcSlotPK) obj;
		if (bookingSlot == null) {
			if (other.bookingSlot != null)
				return false;
		} else if (!bookingSlot.equals(other.bookingSlot))
			return false;
		if (dcNbr != other.dcNbr)
			return false;
		return true;
	}

	

}