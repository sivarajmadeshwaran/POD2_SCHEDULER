package com.scheduler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scheduler.Repository.TruckRepository;
import com.scheduler.entity.Truck;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

/**
 * This is for Truck Domain Business Implementation
 */

@Component
public class TruckServiceImpl implements TruckService{

	@Autowired
	private TruckRepository truckRepository;
	
	
	/**
	 *  This is to persist the Truck detail.
	 * @param Truck DTO
	 * @return void
	 */
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

	
	/**
	 *  This is to get all the truck details
	*/
	public Object findAllTrucks() {
		return truckRepository.findAll();
	}

	
	/**
	 *  This is to get truck details based on ID.
	 *  @param Truck Id 
	*/
	public Object findTruckById(int id) throws ResourceNotFoundException {
		return truckRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("No details found for Truck : " + id));
	}

	
	
	/**
	 *  This is to modify truck details based on ID.
	 *  @param Truck Id , Truck DTO
	*/
	public void updateTruck(Truck truck, int truck_id) throws ResourceNotFoundException  {
		truckRepository.findById(truck_id)
        .orElseThrow(() -> new ResourceNotFoundException("Truck " + truck_id + " is not found to update"));
		
		truck.setTruckName(truck.getTruckName());
		truckRepository.save(truck);
	}
	
	
	/**
	 *  This is to remove truck details based on ID.
	 *  @param Truck Id 
	*/
	public void deleteTruckById(int id) throws ResourceNotFoundException {
		truckRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Truck id "  + id + " is not available to remove the data"));
		truckRepository.deleteById(id);
	}

}