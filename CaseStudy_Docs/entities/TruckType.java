package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the truck_type database table.
 * 
 */
@Entity
@Table(name="truck_type")
@NamedQuery(name="TruckType.findAll", query="SELECT t FROM TruckType t")
public class TruckType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Column(name="type_description")
	private String typeDescription;

	//bi-directional many-to-one association to Truck
	@OneToMany(mappedBy="truckTypeBean")
	private List<Truck> trucks;

	public TruckType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public List<Truck> getTrucks() {
		return this.trucks;
	}

	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}

	public Truck addTruck(Truck truck) {
		getTrucks().add(truck);
		truck.setTruckTypeBean(this);

		return truck;
	}

	public Truck removeTruck(Truck truck) {
		getTrucks().remove(truck);
		truck.setTruckTypeBean(null);

		return truck;
	}

}