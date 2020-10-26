package com.scheduler.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.scheduler.entity.Truck;
import com.scheduler.entity.TruckType;
import com.scheduler.entity.Vendor;

public class TruckControllerTest extends AbstractTest{

	 @Override
	 @BeforeEach
	 public void setUp() {
	     super.setUp();
	 }
	 
	 @Test
	 public void getTruckList() throws Exception {
	    String uri = "http://localhost:8080/setup/truck";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    Truck[] Trucklist = super.mapFromJson(content, Truck[].class);
	    assertTrue(Trucklist.length > 0);
	}
	 
	 @Test
	 public void createTruck() throws Exception {
	    String uri = "http://localhost:8080/setup/truck";
	    
	    TruckType type=new TruckType();
	    type.setId(1);
	    type.setTypeDescription("Straight Truck");
	    type.setCreatedTimestamp(new Date());
	    
	    Truck truck=new Truck();
	    truck.setTruckNbr(1234);
	    truck.setTruckName("JBL");
	    truck.setTruckTypeBean(type);
	    truck.setCreatedTimestamp(new Date());
	    truck.setLastUpdatedTimestamp(new Date());	    
	    
	    String inputJson = super.mapToJson(truck);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(201, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "Truck detail is added successfully");
	}
	 
	@Test
	public void updateTruck() throws Exception {
	    String uri = "http://localhost:8080/setup/truck/1234";
	    
	    TruckType type=new TruckType();
	    type.setId(1);
	    type.setTypeDescription("Straight Truck");
	    type.setCreatedTimestamp(new Date());
	    
	    Truck truck=new Truck();
	    truck.setTruckNbr(1234);
	    truck.setTruckName("PRO JBL");
	    truck.setTruckTypeBean(type);
	    truck.setCreatedTimestamp(new Date());
	    truck.setLastUpdatedTimestamp(new Date());
	    
	    String inputJson = super.mapToJson(truck);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "Truck detail is updated successsfully");
	 }
	
	 @Test
	 public void deleteTruck() throws Exception {
	      String uri = "http://localhost:8080/setup/truck/1234";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Truck detail removed successfully");
	 }

	@Test
	public void whenCreateTruckHasError() throws Exception {
		String uri = "http://localhost:8080/setup/truck";

		TruckType type = new TruckType();
		type.setId(-1);
		type.setTypeDescription("Straight Truck");
		type.setCreatedTimestamp(new Date());

		Truck truck = new Truck();
		truck.setTruckNbr(1234);
		truck.setTruckName("JBL");
		truck.setTruckTypeBean(type);
		truck.setCreatedTimestamp(new Date());
		truck.setLastUpdatedTimestamp(new Date());

		String inputJson = super.mapToJson(truck);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(409, status);
	}

	 @Test
	 public void when_NoTrucksById() throws Exception {
	    String uri = "http://localhost:8080/setup/truck/5";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(204, status);
	}

	 @Test
	 public void when_NoTruckToDelete() throws Exception {
	    String uri = "http://localhost:8080/setup/truck/5";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(204, status);
	}

}
