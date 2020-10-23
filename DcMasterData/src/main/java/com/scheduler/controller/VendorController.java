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

import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	
	
	@Autowired
	private VendorService vendorService;

	
	// Create new vendor
	@PostMapping
	public ResponseEntity<Object> createVendor(@RequestBody Vendor vendor) throws ResourceExistsException,Exception {
		vendorService.addVendorDetail(vendor);
		return new ResponseEntity<>("Vendor detail is added successfully", HttpStatus.CREATED);
	}
	
	
	// Find all vendors
	@GetMapping
	public ResponseEntity<Object> findAllVendors(){
		 return new ResponseEntity<>(vendorService.findAllVendors(), HttpStatus.OK);
	}
	
	// Find vendor by Id
	@GetMapping("/{id}")
	public ResponseEntity<Object> getVendorById(@PathVariable (value="id") int id) throws ResourceNotFoundException{
		return new ResponseEntity<>(vendorService.getVendorById(id), HttpStatus.OK);
	}

	// Modify vendor by Id 
	@PutMapping("/{id}")
	public ResponseEntity<Object> modifyVendor(@PathVariable ("id") int vendor_id,@RequestBody Vendor vendor) throws ResourceNotFoundException {
		vendorService.updateVendorDetail(vendor_id,vendor);
	    return new ResponseEntity<>("Vendor detail is updated successsfully", HttpStatus.OK);
	}
	
	// Remove vendor by Id
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteVendor(@PathVariable ("id") int vendor_id) throws ResourceNotFoundException {
		vendorService.deleteVendorDetail(vendor_id);
		return new ResponseEntity<>("Vendor detail is removed successfully", HttpStatus.OK);
	}

}
