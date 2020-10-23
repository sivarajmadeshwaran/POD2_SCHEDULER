package com.scheduler.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scheduler.entity.Truck;
import com.scheduler.entity.TruckType;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.TruckServiceImpl;

@SpringBootTest
public class TruckServiceTest {

	Truck truck;
	TruckType type;
	
	@Autowired
	private TruckServiceImpl truckServiceImpl;
	
	@BeforeEach
	public void setup() {
		type=new TruckType();
	    type.setId(1);
	    type.setTypeDescription("Straight Truck");
	    type.setCreatedTimestamp(new Date());
	    
	    truck=new Truck();
	    truck.setTruckNbr(1234);
	    truck.setTruckName("JBL PRO");
	    truck.setTruckTypeBean(type);
	    truck.setCreatedTimestamp(new Date());
	    truck.setLastUpdatedTimestamp(new Date());
	    	    	    
	    List<Truck> trucks=new ArrayList<>();
	    trucks.add(truck);
	}
	
	@Test
	public void testCreateTruck() throws Exception {
		truckServiceImpl.addNewTruck(truck);
	}

	    
	@Test
	public void testGetAllTrucks() {
		truckServiceImpl.findAllTrucks();
	}

	@Test
	public void testUpdateTruck() throws ResourceNotFoundException {
		truckServiceImpl.updateTruck(truck, 1234);
	}
	
	@Test
	public void testDeleteTruck() throws ResourceNotFoundException {
		truckServiceImpl.deleteTruckById(1234);
	}

}
