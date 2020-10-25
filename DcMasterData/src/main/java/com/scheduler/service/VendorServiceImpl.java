package com.scheduler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.Repository.VendorRepository;
import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

/**
 * This is for Vendor Domain Business Implementation
*/

@Service
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	/**
	 *  This is to persist the Vendor detail.
	 * @param Vendor DTO
	 * @return void
	*/
	public void addVendorDetail(Vendor vendor) throws ResourceExistsException,Exception {
		int vendorId=vendor.getId();
		Optional<Vendor> id = vendorRepository.findById(vendorId);
		String mail=vendor.getMail();
		String name=vendor.getName();
		String address=vendor.getAddress();
		int phone=vendor.getPhone();
		
		if(id.isEmpty() && mail.length()<=45 && name.length()<=45 && address.length()<=100 && phone>0) {
		 vendorRepository.save(vendor);
		}
		else if(!id.isEmpty()) { throw new ResourceExistsException("Vendor " + vendorId + " is already avaiable");}
		else throw new Exception("Name / Mail / Address / Phone - Out of range");
	}

	
	/**
	 *  This is to get all the vendor details
	*/
	public Object findAllVendors() {
		return vendorRepository.findAll();
	}
	
	
	/**
	 *  This is to get vendor details based on ID.
	 *  @param Vendor Id 
	*/
	public Object getVendorById(int id) throws ResourceNotFoundException{
		return vendorRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Vendor details not found for this id : " + id));
	}
	
	
	/**
	 *  This is to modify vendor details based on ID.
	 *  @param Vendor Id , Vendor DTO
	*/
	public void updateVendorDetail(int vendor_id, Vendor vendor) throws ResourceNotFoundException {
	
		vendorRepository.findById(vendor_id)
        .orElseThrow(() -> new ResourceNotFoundException("vendor not found for this id to update the details : " + vendor_id));
		
		vendor.setName(vendor.getName());
		vendor.setPhone(vendor.getPhone());
		vendor.setMail(vendor.getMail());
		vendor.setAddress(vendor.getAddress());
		vendorRepository.save(vendor);
	}

	/**
	 *  This is to remove vendor details based on ID.
	 *  @param Vendor Id 
	*/
	public void deleteVendorDetail(int id) throws ResourceNotFoundException {
		vendorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vendor id "  + id + "is not available to remove the data"));
		vendorRepository.deleteById(id);
	}

}
