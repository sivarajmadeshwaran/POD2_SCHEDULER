package com.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scheduler.entity.Truck;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.TruckService;

/**
 * This is  to expose Truck Domain related API's
*/

@RestController
@RequestMapping("/setup/truck")
public class TruckController {
	
	@Autowired
	private TruckService truckService;
	

	// Create new truck
	@PostMapping
	public ResponseEntity<Object> createTruck(@RequestBody Truck truck) throws ResourceExistsException,Exception{
		truckService.addNewTruck(truck);
		return new ResponseEntity<>("Truck detail is added successfully", HttpStatus.CREATED);
	}

	// Find all Trucks
	@GetMapping
	public ResponseEntity<Object> findTrucks() {
		return new ResponseEntity<>(truckService.findAllTrucks(), HttpStatus.OK);
	}

	// Retrieve Truck by Id
	@GetMapping("/{id}")
	public ResponseEntity<Object> getTruckById(@PathVariable (value="id") int id)  throws ResourceNotFoundException{
		return new ResponseEntity<>(truckService.findTruckById(id), HttpStatus.OK);
	}

	// Modify Truck detail
	@PutMapping("/{id}")
	public ResponseEntity<Object> modifyTruck(@RequestBody Truck truck, @PathVariable ("id") int truck_id) throws ResourceNotFoundException {
		truckService.updateTruck(truck,truck_id);
		return new ResponseEntity<>("Truck detail is updated successsfully", HttpStatus.OK);
		
	}
	
	// Remove Truck
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> removeTruck(@PathVariable int id) throws ResourceNotFoundException  {
		 truckService.deleteTruckById(id);
		 return new ResponseEntity<>("Truck detail removed successfully", HttpStatus.OK);
	}

}