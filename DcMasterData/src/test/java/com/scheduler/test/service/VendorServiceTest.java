package com.scheduler.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.VendorServiceImpl;

@SpringBootTest
public class VendorServiceTest {

	Vendor vendor;
	
	@Autowired
	private VendorServiceImpl serviceImpl;
	
	@BeforeEach
	public void setup() {
	
		vendor = new Vendor();
		vendor.setId(1);
	    vendor.setName("walmartDc");
	    vendor.setPhone(987456321);
	    vendor.setMail("walmart@mail.com");
	    vendor.setAddress("US");
	    vendor.setCreatedDt(new Date());
	    vendor.setLastUpdatedDt(new Date());
	    
	    Vendor vendor1 = new Vendor();
		vendor1.setId(2);
	    vendor1.setName("walmart");
	    vendor1.setPhone(987456);
	    vendor1.setMail("walmartDC@mail.com");
	    vendor1.setAddress("USA");
	    vendor1.setCreatedDt(new Date());
	    vendor1.setLastUpdatedDt(new Date());
	    
	    List<Vendor> vendors=new ArrayList<>();
	    vendors.add(vendor1);
	    vendors.add(vendor);
	}
	
	@Test
	public void testGetAllVendors() {
		serviceImpl.findAllVendors();
	}

	@Test
	public void testCreateVendor() throws Exception {
		serviceImpl.addVendorDetail(vendor);
	}

	@Test
	public void testUpdateVendor() throws ResourceNotFoundException {
		serviceImpl.updateVendorDetail(2, vendor);
	}
	
	@Test
	public void testDeleteVendor() throws ResourceNotFoundException {
		serviceImpl.deleteVendorDetail(1);
	}

}
