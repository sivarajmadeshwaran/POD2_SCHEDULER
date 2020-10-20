package com.scheduler.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name="truck_type")
public class TruckType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;

	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@NotNull
	private Date createdTimestamp;

	@Column(name="type_description")
	private String typeDescription;

	//bi-directional many-to-one association to Truck
	//@OneToMany(mappedBy="truckTypeBean")
	//private List<Truck> trucks;

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



/*	public List<Truck> getTrucks() {

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

*/

}