package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the truck database table.
 * 
 */
@Entity
@NamedQuery(name="Truck.findAll", query="SELECT t FROM Truck t")
public class Truck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="truck_nbr")
	private int truckNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	//bi-directional many-to-one association to TruckType
	@ManyToOne
	@JoinColumn(name="truck_type")
	private TruckType truckTypeBean;

	public Truck() {
	}

	public int getTruckNbr() {
		return this.truckNbr;
	}

	public void setTruckNbr(int truckNbr) {
		this.truckNbr = truckNbr;
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

	public TruckType getTruckTypeBean() {
		return this.truckTypeBean;
	}

	public void setTruckTypeBean(TruckType truckTypeBean) {
		this.truckTypeBean = truckTypeBean;
	}

}