package com.scheduler.service;

import com.scheduler.entity.Truck;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

public interface TruckService {
	
	public void addNewTruck(Truck truck) throws ResourceExistsException, Exception;
	public Object findAllTrucks();
	public Object findTruckById(int id) throws ResourceNotFoundException;
	public void updateTruck(Truck truck, int truck_id) throws ResourceNotFoundException;
	public void deleteTruckById(int id) throws ResourceNotFoundException;

}
