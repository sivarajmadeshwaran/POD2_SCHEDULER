package com.atlas.scheduler.purchaseorder.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the purchase_order database table.
 * 
 */
@Entity
@Table(name = "purchase_order")
@Data
@NoArgsConstructor
public class PurchaseOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "po_nbr")
	private int poNbr;

	private String address;

	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;

	@Column(name = "last_updated_timestamp")
	private LocalDateTime lastUpdatedTimestamp;

	@Temporal(TemporalType.DATE)
	@Column(name = "po_date")
	private Date poDate;

	@Column(name = "vendor_nbr")
	private Integer vendor;

	// bi-directional many-to-one association to PurchaseOrderLine
	@OneToMany(mappedBy = "purchaseOrder", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<PurchaseOrderLine> purchaseOrderLines;

}