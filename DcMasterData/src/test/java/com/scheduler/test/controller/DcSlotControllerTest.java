package com.scheduler.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;
import com.scheduler.entity.Vendor;

public class DcSlotControllerTest extends AbstractTest{

	 @Override
	 @BeforeEach
	 public void setUp() {
	     super.setUp();
	 }
	 
	 @Test
	 public void getDcSlotList() throws Exception {
	    String uri = "http://localhost:8080/setup/dcslot";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    DcSlot[] DcSlotlist = super.mapFromJson(content, DcSlot[].class);
	    assertTrue(DcSlotlist.length > 0);
	}
	 
	 @Test
	 public void createDcSlot() throws Exception {
	    String uri = "http://localhost:8080/setup/dcslot";
	    
	    DcSlotPK pk=new DcSlotPK();
	    pk.setDcNbr(7560);
	    pk.setBookingSlot("07:00-08:00");
	    
	    DcSlot dcslot=new DcSlot();
	    dcslot.setMaxTrucks(15);
	    dcslot.setSlotId(pk);
	    dcslot.setObsolete_indicator("N");
	    dcslot.setCreatedTimestamp(new Date());
	    dcslot.setLastUpdatedTimestamp(new Date());
	    
	    String inputJson = super.mapToJson(dcslot);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(201, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "DcSlot detail is added successfully");
	}
	 
	@Test
	public void updateDc() throws Exception {
	    String uri = "http://localhost:8080/setup/dcslot/7560/07:00-08:00";
	    
	    DcSlotPK pk=new DcSlotPK();
	    pk.setDcNbr(7560);
	    pk.setBookingSlot("07:00-08:00");
	    
	    DcSlot dcslot=new DcSlot();
	    dcslot.setId(1);
	    dcslot.setMaxTrucks(20);
	    dcslot.setSlotId(pk);
	    dcslot.setObsolete_indicator("N");
	    dcslot.setCreatedTimestamp(new Date());
	    dcslot.setLastUpdatedTimestamp(new Date());
	    
	    String inputJson = super.mapToJson(dcslot);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	 }
	
	 @Test
	 public void deleteDc() throws Exception {
	      String uri = "http://localhost:8080/setup/dcslot/7560/07:00-08:00";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	 }

	 @Test
	 public void when_CreateDcSlotHasError() throws Exception {
	    String uri = "http://localhost:8080/setup/dcslot";
	    DcSlotPK pk=new DcSlotPK();
	    pk.setDcNbr(6561);
	    pk.setBookingSlot("07:00-08:00");
	    
	    DcSlot dcslot=new DcSlot();
	    dcslot.setMaxTrucks(15);
	    dcslot.setSlotId(pk);
	    dcslot.setObsolete_indicator("N");
	    dcslot.setCreatedTimestamp(new Date());
	    dcslot.setLastUpdatedTimestamp(new Date());
	    
	    String inputJson = super.mapToJson(dcslot);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(409, status);
	 }

	 @Test
	 public void when_NoDcSlotToDelete() throws Exception {
	    String uri = "http://localhost:8080/setup/dcslot/6562/07:00-08:00";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(204, status);
	}


}
