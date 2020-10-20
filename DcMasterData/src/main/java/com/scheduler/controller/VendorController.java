package com.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.VendorNotFoundException;
import com.scheduler.Repository.VendorRepository;
import com.scheduler.entity.Vendor;
import com.scheduler.service.DcService;
import com.scheduler.service.VendorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
	
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@PostMapping
	public Vendor saveVendor(@RequestBody Vendor vendor) {
		return vendorService.addVendor(vendor);
	}
	
	@GetMapping
	public List<Vendor> findAllVendors(){
		return vendorService.getVendors();
	}
	
	@GetMapping("/{id}")
	public Optional<Vendor> getVendorById(@PathVariable (value="id") int id){
		return vendorService.getVendorById(id);
	}

	@PutMapping("/{id}")
	public Vendor updateVendor(@RequestBody Vendor vendor, @PathVariable ("id") int vendor_id) {
		Vendor existId= this.vendorRepository.findById(vendor_id)
				.orElseThrow(() -> new VendorNotFoundException("Vendor Is Not Available " + vendor_id) );
		existId.setName(vendor.getName());
		existId.setPhone(vendor.getPhone());
		existId.setMail(vendor.getMail());
		existId.setAddress(vendor.getAddress());
		return this.vendorRepository.save(existId);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable ("id") int vendor_id) {
		vendorService.deleteVendor(vendor_id);
		return ResponseEntity.ok().build();
	}
	
	/*
	@Autowired
	private VendorRepository vendorRepository;
	
	// get all vendor
	
	@GetMapping
	public List<Vendor> getAllVendor(){
			return this.vendorRepository.findAll();
	}
	
	// get vendor by id
	
	@GetMapping("/{id}")
	public Vendor getVendorById(@PathVariable (value="id") int id) {
		return this.vendorRepository.findById(id)
				.orElseThrow(() -> new VendorNotFoundException("Dc Type Is Not Available : " + id) );
		}
		
	// Create vendor
	
	@PostMapping
	public Vendor createvendor(@RequestBody Vendor vendor) {
		return this.vendorRepository.save(vendor);
	}
	
	//update vendor by id
	
	@PutMapping("/{id}")
	public Vendor updateVendor(@RequestBody Vendor vendor, @PathVariable ("id") int vendor_id) {
		Vendor existId= this.vendorRepository.findById(vendor_id)
				.orElseThrow(() -> new VendorNotFoundException("Vendor Is Not Available " + vendor_id) );
		existId.setName(vendor.getName());
		existId.setPhone(vendor.getPhone());
		existId.setMail(vendor.getMail());
		existId.setAddress(vendor.getAddress());
		return this.vendorRepository.save(existId);
		
	}

	// delete Vendor by Id
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Vendor> deleteVendorById(@PathVariable ("id") int vendor_id){
		Vendor existId= this.vendorRepository.findById(vendor_id)
					.orElseThrow(() -> new VendorNotFoundException("Vendor Is Not Available :" +vendor_id) );
			this.vendorRepository.delete(existId);
			return ResponseEntity.ok().build();
	}
	*/
}
