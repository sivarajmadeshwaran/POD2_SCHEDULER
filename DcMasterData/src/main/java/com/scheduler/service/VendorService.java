package com.scheduler.service;

import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

public interface VendorService {

	public void addVendorDetail(Vendor vendor) throws ResourceExistsException, Exception;
	public Object getVendorById(int id) throws ResourceNotFoundException;
	public void deleteVendorDetail(int id) throws ResourceNotFoundException;
	public Object findAllVendors();
	public void updateVendorDetail(int vendor_id, Vendor vendor) throws ResourceNotFoundException;
}
