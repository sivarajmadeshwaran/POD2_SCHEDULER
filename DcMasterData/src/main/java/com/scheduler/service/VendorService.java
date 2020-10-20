package com.scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.Repository.VendorRepository;
import com.scheduler.entity.Vendor;

@Service
public class VendorService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	public Vendor addVendor(Vendor vendor) {
		      return vendorRepository.save(vendor);
	    }

	public List<Vendor> getVendors() {
		List<Vendor> vendors = vendorRepository.findAll();
		return vendors;
	}

	public Optional<Vendor> getVendorById(int id) {
		return  this.vendorRepository.findById(id);
	}
	
	public void deleteVendor(int id) {
		vendorRepository.deleteById(id);
	}

	
}
