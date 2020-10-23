package com.scheduler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scheduler.Repository.DcRepository;
import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.Dc;
import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;
import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

@Component
public class DcServiceImpl implements DcService{

	@Autowired
	private DcRepository dcRepository;

	
	// Add new Dc
	public void createNewDC(Dc dc) throws ResourceExistsException,Exception {
		int dcNbr=dc.getDc_number();
		Optional<Dc> id = dcRepository.findById(dcNbr);
		String name=dc.getDc_city();
		
		if(id.isEmpty()  && name.length()<=45 ) {
			dcRepository.save(dc);
		}
		else if(!id.isEmpty()) { throw new ResourceExistsException("Dc " + dcNbr + " is already avaiable");}
		else throw new Exception("Name - Out of range");
		
	}

	// Find all Dcs
	public Object findDcs() {
		return dcRepository.findAll();
	}
	
	// Retrieve Dc by Id
	public Object findDcById(int id) throws ResourceNotFoundException {
		return dcRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Dc detail is not found for : "+ id  ));
	}

	// Modify existing Dc Detail
	public void updateDc(Dc dc, int dc_id) throws ResourceNotFoundException {
		
		dcRepository.findById(dc_id)
		        .orElseThrow(() -> new ResourceNotFoundException("Dc " + dc_id + " is not found to update the details"  ));
		
		dc.setDc_city(dc.getDc_city());
		dcRepository.save(dc);
	}
	
	// Remove Dc by Id
	public void removeDcById(int id) throws ResourceNotFoundException {
		dcRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Dc "  + id + "is not available to remove the data"));
		dcRepository.deleteById(id);
	}

}