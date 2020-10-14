package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the vendor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendor.findAll", query="SELECT v FROM Vendor v")
public class Vendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	private String name;

	@Column(name="phone_nbr")
	private int phoneNbr;

	//bi-directional many-to-one association to PurchaseOrder
	@OneToMany(mappedBy="vendor")
	private List<PurchaseOrder> purchaseOrders;

	public Vendor() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNbr() {
		return this.phoneNbr;
	}

	public void setPhoneNbr(int phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	public List<PurchaseOrder> getPurchaseOrders() {
		return this.purchaseOrders;
	}

	public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
		getPurchaseOrders().add(purchaseOrder);
		purchaseOrder.setVendor(this);

		return purchaseOrder;
	}

	public PurchaseOrder removePurchaseOrder(PurchaseOrder purchaseOrder) {
		getPurchaseOrders().remove(purchaseOrder);
		purchaseOrder.setVendor(null);

		return purchaseOrder;
	}

}