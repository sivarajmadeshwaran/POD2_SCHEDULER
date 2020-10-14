package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the purchase_order_line database table.
 * 
 */
@Embeddable
public class PurchaseOrderLinePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="po_nbr", insertable=false, updatable=false)
	private int poNbr;

	@Column(name="po_line_nbr")
	private int poLineNbr;

	public PurchaseOrderLinePK() {
	}
	public int getPoNbr() {
		return this.poNbr;
	}
	public void setPoNbr(int poNbr) {
		this.poNbr = poNbr;
	}
	public int getPoLineNbr() {
		return this.poLineNbr;
	}
	public void setPoLineNbr(int poLineNbr) {
		this.poLineNbr = poLineNbr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PurchaseOrderLinePK)) {
			return false;
		}
		PurchaseOrderLinePK castOther = (PurchaseOrderLinePK)other;
		return 
			(this.poNbr == castOther.poNbr)
			&& (this.poLineNbr == castOther.poLineNbr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.poNbr;
		hash = hash * prime + this.poLineNbr;
		
		return hash;
	}
}