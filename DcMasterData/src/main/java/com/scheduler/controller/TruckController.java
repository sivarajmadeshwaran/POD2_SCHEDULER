package com.scheduler.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.TruckNotFoundException;
import com.scheduler.Repository.TruckRepository;
import com.scheduler.entity.Truck;
import com.scheduler.service.TruckService;



@RestController
@RequestMapping("/api")
public class TruckController {
	
	@Autowired
	private TruckService truckService;
	
	@Autowired
	private TruckRepository truckRepository;

	@PostMapping("/addTruck")
	public String addTruck(@RequestBody Truck truck) {
	return truckService.saveTruck(truck);
	}

	@PostMapping("/addTrucks")
	public String addDCs(@RequestBody List<Truck> trucks) {
	return truckService.saveTruckList(trucks);
	}

	@GetMapping("/getTrucks")
	public List<Truck> getTrucks() {
		return truckService.getTruckList();
		}

	@DeleteMapping("/deleteTruck/{id}")
	public String deleteTruck(@PathVariable int id) {
		return truckService.deleteTruckById(id);
	}

	@PutMapping("/updateTruck/{id}")
	public Truck updateDc(@RequestBody Truck truck, @PathVariable ("id") int truck_id) {
		Truck existId= this.truckRepository.findById(truck_id)
				.orElseThrow(() -> new TruckNotFoundException("Id Is Not Available " + truck_id) );
		existId.setTruckName(truck.getTruckName());
		return this.truckRepository.save(existId);
		
	}
	
	@GetMapping("/getTruckById/{id}")
	public Optional<Truck> getTruckById(@PathVariable (value="id") int id){
		return truckService.getTruckById(id);
	}


}