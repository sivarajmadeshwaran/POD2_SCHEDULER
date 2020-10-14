package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dc_slot database table.
 * 
 */
@Embeddable
public class DcSlotPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="dc_nbr")
	private int dcNbr;

	@Column(name="booking_slot")
	private String bookingSlot;

	public DcSlotPK() {
	}
	public int getDcNbr() {
		return this.dcNbr;
	}
	public void setDcNbr(int dcNbr) {
		this.dcNbr = dcNbr;
	}
	public String getBookingSlot() {
		return this.bookingSlot;
	}
	public void setBookingSlot(String bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DcSlotPK)) {
			return false;
		}
		DcSlotPK castOther = (DcSlotPK)other;
		return 
			(this.dcNbr == castOther.dcNbr)
			&& this.bookingSlot.equals(castOther.bookingSlot);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dcNbr;
		hash = hash * prime + this.bookingSlot.hashCode();
		
		return hash;
	}
}