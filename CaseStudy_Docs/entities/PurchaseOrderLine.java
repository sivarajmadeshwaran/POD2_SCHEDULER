package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the purchase_order_line database table.
 * 
 */
@Entity
@Table(name="purchase_order_line")
@NamedQuery(name="PurchaseOrderLine.findAll", query="SELECT p FROM PurchaseOrderLine p")
public class PurchaseOrderLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PurchaseOrderLinePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Column(name="item_description")
	private String itemDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	@Column(name="ordered_qty")
	private int orderedQty;

	@Column(name="upc_nbr")
	private int upcNbr;

	//bi-directional many-to-one association to PurchaseOrder
	@ManyToOne
	@JoinColumn(name="po_nbr")
	private PurchaseOrder purchaseOrder;

	public PurchaseOrderLine() {
	}

	public PurchaseOrderLinePK getId() {
		return this.id;
	}

	public void setId(PurchaseOrderLinePK id) {
		this.id = id;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public int getOrderedQty() {
		return this.orderedQty;
	}

	public void setOrderedQty(int orderedQty) {
		this.orderedQty = orderedQty;
	}

	public int getUpcNbr() {
		return this.upcNbr;
	}

	public void setUpcNbr(int upcNbr) {
		this.upcNbr = upcNbr;
	}

	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

}