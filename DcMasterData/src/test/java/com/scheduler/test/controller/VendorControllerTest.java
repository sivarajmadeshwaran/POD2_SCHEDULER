package com.scheduler.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.scheduler.entity.Vendor;

public class VendorControllerTest extends AbstractTest{

	 @Override
	 @BeforeEach
	 public void setUp() {
	     super.setUp();
	 }
	 
	 @Test
	 public void createVendor() throws Exception {
	    String uri = "http://localhost:8080/vendor";
	    Vendor vendor = new Vendor();
	    vendor.setId(19);
	    vendor.setName("walmartDc");
	    vendor.setPhone(987456321);
	    vendor.setMail("walmart@mail.com");
	    vendor.setAddress("US");
	    vendor.setCreatedDt(new Date());
	    vendor.setLastUpdatedDt(new Date());
	    String inputJson = super.mapToJson(vendor);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(201, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    assertEquals(content, "Vendor detail is added successfully");
	 }
	 
	 @Test
	 public void getVendorsList() throws Exception {
	    String uri = "http://localhost:8080/vendor";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    String content = mvcResult.getResponse().getContentAsString();
	    Vendor[] vendorlist = super.mapFromJson(content, Vendor[].class);
	    assertTrue(vendorlist.length > 0);
	}

	@Test
	public void updateVendor() throws Exception {
		String uri = "http://localhost:8080/vendor/19";
		Vendor vendor = new Vendor();
		vendor.setId(19);
	    vendor.setName("walmartDc19");
	    vendor.setPhone(987456321);
	    vendor.setMail("walmart19@mail.com");
	    vendor.setAddress("USa");
		String inputJson = super.mapToJson(vendor);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Vendor detail is updated successsfully");
	}

	@Test
	public void deleteVendor() throws Exception {
		String uri = "http://localhost:8080/vendor/19";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Vendor detail is removed successfully");
	}

	 @Test
	 public void whenCreateVendorHasError() throws Exception {
	    String uri = "http://localhost:8080/vendor";
	    Vendor vendor = new Vendor();
	    vendor.setId(19);
	    vendor.setName("walmartDc");
	    vendor.setPhone(-987456321);
	    vendor.setMail("walmart@mail.com");
	    vendor.setAddress("US");
	    vendor.setCreatedDt(new Date());
	    vendor.setLastUpdatedDt(new Date());
	    String inputJson = super.mapToJson(vendor);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(409, status);
	 }

	 @Test
	 public void when_NoVendorsById() throws Exception {
	    String uri = "http://localhost:8080/vendor/20";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(204, status);
	}

	 @Test
	 public void when_NoVendorsDelete() throws Exception {
	    String uri = "http://localhost:8080/vendor/20";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(204, status);
	}

	
}
