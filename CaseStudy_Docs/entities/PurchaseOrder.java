package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the purchase_order database table.
 * 
 */
@Entity
@Table(name="purchase_order")
@NamedQuery(name="PurchaseOrder.findAll", query="SELECT p FROM PurchaseOrder p")
public class PurchaseOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="po_nbr")
	private int poNbr;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	@Temporal(TemporalType.DATE)
	@Column(name="po_date")
	private Date poDate;

	//bi-directional many-to-many association to Appointment
	@ManyToMany
	@JoinTable(
		name="appointment_po"
		, joinColumns={
			@JoinColumn(name="po_nbr")
			}
		, inverseJoinColumns={
			@JoinColumn(name="appointment_id")
			}
		)
	private List<Appointment> appointments;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name="vendor_nbr")
	private Vendor vendor;

	//bi-directional many-to-one association to PurchaseOrderLine
	@OneToMany(mappedBy="purchaseOrder")
	private List<PurchaseOrderLine> purchaseOrderLines;

	public PurchaseOrder() {
	}

	public int getPoNbr() {
		return this.poNbr;
	}

	public void setPoNbr(int poNbr) {
		this.poNbr = poNbr;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public Date getPoDate() {
		return this.poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<PurchaseOrderLine> getPurchaseOrderLines() {
		return this.purchaseOrderLines;
	}

	public void setPurchaseOrderLines(List<PurchaseOrderLine> purchaseOrderLines) {
		this.purchaseOrderLines = purchaseOrderLines;
	}

	public PurchaseOrderLine addPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		getPurchaseOrderLines().add(purchaseOrderLine);
		purchaseOrderLine.setPurchaseOrder(this);

		return purchaseOrderLine;
	}

	public PurchaseOrderLine removePurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		getPurchaseOrderLines().remove(purchaseOrderLine);
		purchaseOrderLine.setPurchaseOrder(null);

		return purchaseOrderLine;
	}

}