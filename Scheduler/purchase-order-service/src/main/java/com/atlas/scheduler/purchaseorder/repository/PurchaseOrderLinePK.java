package com.atlas.scheduler.purchaseorder.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the purchase_order_line database table.
 * 
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseOrderLinePK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "po_nbr", insertable = false, updatable = false)
	@EqualsAndHashCode.Include
	private int poNbr;

	@Column(name = "po_line_nbr")
	@EqualsAndHashCode.Include
	private int poLineNbr;

}