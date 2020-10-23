package com.scheduler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scheduler.Repository.TruckRepository;
import com.scheduler.entity.Truck;
import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

@Component
public class TruckServiceImpl implements TruckService{

	@Autowired
	private TruckRepository truckRepository;
	
	// Add new Truck
	public void addNewTruck(Truck truck) throws ResourceExistsException,Exception{
		int truckId=truck.getTruckNbr();
		Optional<Truck> id = truckRepository.findById(truckId);
		String name=truck.getTruckName();
		
		if(id.isEmpty() && name.length()<=45 ) {
			truckRepository.save(truck);
		}
		else if(!id.isEmpty()) { throw new ResourceExistsException("Truck " + truckId + " is already avaiable");}
		else throw new Exception("Name - Out of range");
		 
	}

	// Find all trucks
	public Object findAllTrucks() {
		return truckRepository.findAll();
	}

	// Find truck by Id
	public Object findTruckById(int id) throws ResourceNotFoundException {
		return truckRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("No details found for Truck : " + id));
	}

	// Modify existing Truck Detail
	public void updateTruck(Truck truck, int truck_id) throws ResourceNotFoundException  {
		truckRepository.findById(truck_id)
        .orElseThrow(() -> new ResourceNotFoundException("Truck " + truck_id + " is not found to update"));
		
		truck.setTruckName(truck.getTruckName());
		truckRepository.save(truck);
	}
	
	// Remove Truck by Id
	public void deleteTruckById(int id) throws ResourceNotFoundException {
		truckRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Truck id "  + id + " is not available to remove the data"));
		truckRepository.deleteById(id);
	}

}