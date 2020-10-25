package com.atlas.scheduler.purchaseorder.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the purchase_order_line database table.
 * 
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseOrderLinePK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "po_nbr", insertable = false, updatable = false)
	private int poNbr;

	@Column(name = "po_line_nbr")
	private int poLineNbr;

}