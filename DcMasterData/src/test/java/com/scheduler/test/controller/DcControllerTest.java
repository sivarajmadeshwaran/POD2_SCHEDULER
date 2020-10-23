package com.scheduler.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.scheduler.entity.Dc;
import com.scheduler.entity.DcType;

public class DcControllerTest extends AbstractTest{

	 @Override
	 @BeforeEach
	 public void setUp() {
	     super.setUp();
	 }
	 
	 @Test
	 public void getDcList() throws Exception {
	    String uri = "http://localhost:8080/setup/dc";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    Dc[] Dclist = super.mapFromJson(content, Dc[].class);
	    assertTrue(Dclist.length > 0);
	}
	 
	 @Test
	 public void createDc() throws Exception {
	    String uri = "http://localhost:8080/setup/dc";
	    
	    DcType dcType=new DcType();
	    dcType.setId(1);
	    dcType.setTypeDesc("Regional");
	    dcType.setCreatedAt(new Date());
	    
	    Dc dc=new Dc();
	    dc.setDc_number(7560);
	    dc.setDc_city("MX");
	    dc.setDcTypeBean(dcType);
	    dc.setCreatedAt(new Date());
	    dc.setUpdatedAt(new Date());
	    
	    String inputJson = super.mapToJson(dc);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(201, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "Dc detail is added successfully");
	}
	 
	@Test
	public void updateDc() throws Exception {
	    String uri = "http://localhost:8080/setup/dc/7560";
	    
	    DcType dcType=new DcType();
	    dcType.setId(1);
	    dcType.setTypeDesc("Regional");
	    dcType.setCreatedAt(new Date());
	    
	    Dc dc=new Dc();
	    dc.setDc_number(7560);
	    dc.setDc_city("CN");
	    dc.setDcTypeBean(dcType);
	    dc.setCreatedAt(new Date());
	    dc.setUpdatedAt(new Date());
	    
	    String inputJson = super.mapToJson(dc);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "Dc detail is updated successsfully");
	 }
	
	 @Test
	 public void deleteDc() throws Exception {
	      String uri = "http://localhost:8080/setup/dc/7560";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Dc detail removed successfully");
	 }
}
