package com.atlas.scheduler.purchaseorder.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the purchase_order_line database table.
 * 
 */
@Entity
@Table(name = "purchase_order_line")
@NamedQuery(name = "PurchaseOrderLine.findAll", query = "SELECT p FROM PurchaseOrderLine p")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PurchaseOrderLinePK id;

	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "last_updated_timestamp")
	private LocalDateTime lastUpdatedTimestamp;

	@Column(name = "ordered_qty", updatable = true)
	private int orderedQty;

	@Column(name = "upc_nbr")
	private int upcNbr;

	// bi-directional many-to-one association to PurchaseOrder
	@ManyToOne
	@JoinColumn(name = "po_nbr", insertable = false, updatable = false)
	private PurchaseOrder purchaseOrder;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PurchaseOrderLine other = (PurchaseOrderLine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}