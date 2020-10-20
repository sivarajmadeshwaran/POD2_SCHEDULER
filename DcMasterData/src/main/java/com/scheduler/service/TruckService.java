package com.scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.scheduler.Repository.TruckRepository;
import com.scheduler.entity.Truck;

@Component
public class TruckService{

	@Autowired
	private TruckRepository truckRepository;
		
	public String saveTruck(Truck truck) {
		System.out.println("Inputs : " + truck);
		truckRepository.save(truck);
		return "Truck is added";
	}

	public String saveTruckList(List<Truck> trucks) {
		System.out.println("Inputs : " + trucks);
		truckRepository.saveAll(trucks);
		return "Truck size is : " + trucks.size();
	}

	public List<Truck> getTruckList() {
		return (List<Truck>) truckRepository.findAll();
	}

	public String deleteTruckById(int id) {
		truckRepository.deleteById(id);
		return "Truck removed !!" + id;
	}

	public Optional<Truck> getTruckById(int id) {
		return this.truckRepository.findById(id);
	}

}