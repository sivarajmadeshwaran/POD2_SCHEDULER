package com.scheduler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;
import com.scheduler.entity.Vendor;
import com.scheduler.exception.ResourceExistsException;

@Service
public class DcSlotServiceImpl implements DcSlotService{

	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	// Add new DcSlot
	public void addDcSlot(DcSlot dcSlot) throws Exception{
		
		String slotTime=dcSlot.getSlotId().getBookingSlot();
		if(slotTime.length()==11) {
			dcSlotRepository.save(dcSlot);
		}
		else throw new Exception("Booking Slot Time - Out of range");
	}
	
	// Find all DcSlots
	public Object getDcSlots() {
		return dcSlotRepository.findAll();
	}

}
